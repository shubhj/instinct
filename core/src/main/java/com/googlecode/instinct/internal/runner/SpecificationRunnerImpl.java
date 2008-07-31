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
import static com.googlecode.instinct.internal.runner.ErrorLocation.AFTER_SPECIFICATION;
import static com.googlecode.instinct.internal.runner.ErrorLocation.AUTO_WIRING;
import static com.googlecode.instinct.internal.runner.ErrorLocation.BEFORE_SPECIFICATION;
import static com.googlecode.instinct.internal.runner.ErrorLocation.CLASS_INITIALISATION;
import static com.googlecode.instinct.internal.runner.ErrorLocation.MOCK_VERIFICATION;
import static com.googlecode.instinct.internal.runner.ErrorLocation.SPECIFICATION;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.ExceptionSanitiserImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import fj.Effect;
import fj.data.List;

@SuppressWarnings({"ParameterNameDiffersFromOverriddenParameter"})
public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final ExceptionSanitiser exceptionSanitiser = new ExceptionSanitiserImpl();
    private final MethodInvoker methodInvoker = new MethodInvokerImpl();

    @SuppressWarnings({"CatchGenericClass", "ThrowableResultOfMethodCallIgnored", "ReturnInsideFinallyBlock"})
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
                        return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
                    } catch (Throwable t) {
                        return fail(startTime, specificationMethod, t, SPECIFICATION);
                    } finally {
                        try {
                            try {
                                runMethods(instance, specificationMethod.getAfterSpecificationMethods());
                            } catch (Throwable t) {
                                return fail(startTime, specificationMethod, t, AFTER_SPECIFICATION);
                            }
                        } finally {
                            try {
                                Mocker.verify();
                            } catch (Throwable t) {
                                return fail(startTime, specificationMethod, t, MOCK_VERIFICATION);
                            }
                        }
                    }
                } catch (Throwable t) {
                    return fail(startTime, specificationMethod, t, BEFORE_SPECIFICATION);
                }
            } catch (Throwable t) {
                return fail(startTime, specificationMethod, t, AUTO_WIRING);
            }
        } catch (Throwable t) {
            return fail(startTime, specificationMethod, t, CLASS_INITIALISATION);
        }
    }

    private void runSpecificationMethod(final Object contextInstance, final LifecycleMethod specificationMethod) {
        methodValidator.checkMethodHasNoParameters(specificationMethod);
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
        methodValidator.checkMethodHasNoParameters(method);
        methodInvoker.invokeMethod(instance, method.getMethod());
    }

    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }

    private SpecificationResult createSpecResult(final LifecycleMethod specificationMethod, final SpecificationRunStatus runStatus,
            final long startTime) {
        final long executionTime = clock.getElapsedTime(startTime);
        return new SpecificationResultImpl(specificationMethod.getName(), runStatus, executionTime);
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    private SpecificationResult fail(final long startTime, final LifecycleMethod specificationMethod, final Throwable exceptionThrown,
            final ErrorLocation location) {
        final SpecificationRunStatus status = new SpecificationRunFailureStatus(exceptionSanitiser.sanitise(exceptionThrown), location);
        return createSpecResult(specificationMethod, status, startTime);
    }
}
