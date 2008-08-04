/*
 * Copyright 2006-2008 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.actor.ActorAutoWirer;
import com.googlecode.instinct.actor.ActorAutoWirerImpl;
import com.googlecode.instinct.expect.behaviour.Mocker;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.reflect.ConstructorInvoker;
import com.googlecode.instinct.internal.reflect.ConstructorInvokerImpl;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiserImpl;
import fj.Effect;
import fj.data.List;
import fj.data.Option;

@SuppressWarnings({"ParameterNameDiffersFromOverriddenParameter"})
public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final ExceptionSanitiser exceptionSanitiser = new ExceptionSanitiserImpl();
    private final MethodInvoker methodInvoker = new MethodInvokerImpl();

    @SuppressWarnings(
            {"CatchGenericClass", "ThrowableResultOfMethodCallIgnored", "ReturnInsideFinallyBlock"})
    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        final long startTime = clock.getCurrentTime();
        try {
            final Class<?> contextClass = specificationMethod.getContextClass();
            final Object instance = invokeConstructor(contextClass);
            Mocker.reset();
            try {
                actorAutoWirer.autoWireFields(instance);
                try {
                    runMethods(instance, specificationMethod.getBeforeSpecificationMethods());
                    try {
                        runSpecificationMethod(instance, specificationMethod);
                        return result(startTime, specificationMethod, SPECIFICATION_SUCCESS);
                    } catch (Throwable t) {
                        return fail(startTime, specificationMethod, t, true);
                    } finally {
                        try {
                            try {
                                runMethods(instance, specificationMethod.getAfterSpecificationMethods());
                            } catch (Throwable t) {
                                return fail(startTime, specificationMethod, t, false);
                            }
                        } finally {
                            try {
                                Mocker.verify();
                            } catch (Throwable t) {
                                return fail(startTime, specificationMethod, t, false);
                            }
                        }
                    }
                } catch (Throwable t) {
                    return fail(startTime, specificationMethod, t, false);
                }
            } catch (Throwable t) {
                return fail(startTime, specificationMethod, t, false);
            }
        } catch (Throwable t) {
            return fail(startTime, specificationMethod, t, false);
        }
    }

    private void runSpecificationMethod(final Object contextInstance, final LifecycleMethod specificationMethod) {
        final Option<Throwable> result = methodValidator.checkMethodHasNoParameters(specificationMethod);
        if (result.isSome()) {
            throw (RuntimeException) result.some();
        }
        methodInvoker.invokeMethod(contextInstance, specificationMethod.getMethod());
    }

    private void runMethods(final Object instance, final List<LifecycleMethod> methods) {
        methods.foreach(new Effect<LifecycleMethod>() {
            public void e(final LifecycleMethod method) {
                runMethod(instance, method);
            }
        });
    }

    private void runMethod(final Object instance, final LifecycleMethod method) {
        final Option<Throwable> result = methodValidator.checkMethodHasNoParameters(method);
        if (result.isSome()) {
            throw (RuntimeException) result.some();
        }
        methodInvoker.invokeMethod(instance, method.getMethod());
    }

    private <T> Object invokeConstructor(final Class<T> cls) {
        final Option<Throwable> result = methodValidator.checkContextConstructor(cls);
        if (result.isSome()) {
            throw (RuntimeException) result.some();
        }
        return constructorInvoker.invokeNullaryConstructor(cls);
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    private SpecificationResult fail(final long startTime, final LifecycleMethod specificationMethod, final Throwable exceptionThrown,
            final boolean expectedExceptionCandidate) {
        final SpecificationRunStatus status =
                new SpecificationRunFailureStatus(exceptionSanitiser.sanitise(exceptionThrown), expectedExceptionCandidate);
        return result(startTime, specificationMethod, status);
    }

    private SpecificationResult result(final long startTime, final LifecycleMethod specificationMethod, final SpecificationRunStatus runStatus) {
        final long executionTime = clock.getElapsedTime(startTime);
        return new SpecificationResultImpl(specificationMethod.getName(), runStatus, executionTime);
    }
}
