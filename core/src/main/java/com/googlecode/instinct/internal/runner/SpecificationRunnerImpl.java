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
import com.googlecode.instinct.internal.util.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.ExceptionSanitiserImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.Effect;
import fj.data.List;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"ParameterNameDiffersFromOverriddenParameter"})
public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final ExceptionSanitiser exceptionSanitiser = new ExceptionSanitiserImpl();
    private final Collection<SpecificationListener> specificationListeners = new ArrayList<SpecificationListener>();
    private final MethodInvoker methodInvoker = new MethodInvokerImpl();

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners.add(specificationListener);
    }

    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        notifyListenersOfPreSpecification(specificationMethod);
        final SpecificationResult specificationResult = runSpecification(specificationMethod);
        notifyListenersOfPostSpecification(specificationMethod, specificationResult);
        return specificationResult;
    }

    @SuppressWarnings({"CatchGenericClass"})
    private SpecificationResult runSpecification(final SpecificationMethod specificationMethod) {
        final long startTime = clock.getCurrentTime();
        try {
            // expose the context class, rather than getting the method
            final Class<?> contextClass = specificationMethod.getContextClass();
            final Object instance = invokeConstructor(contextClass);
            runSpecificationLifecycle(instance, specificationMethod);
            return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
        } catch (Throwable exceptionThrown) {
            final SpecificationRunStatus status = new SpecificationRunFailureStatus(exceptionSanitiser.sanitise(exceptionThrown));
            return createSpecResult(specificationMethod, status, startTime);
        }
    }

    @Suggest({"How do we expose this lifecycle?"})
    private void runSpecificationLifecycle(final Object contextInstance, final SpecificationMethod specificationMethod) {
        Mocker.reset();
        actorAutoWirer.autoWireFields(contextInstance);
        try {
            runMethods(contextInstance, specificationMethod.getBeforeSpecificationMethods());
            runSpecificationMethod(contextInstance, specificationMethod);
        } finally {
            try {
                runMethods(contextInstance, specificationMethod.getAfterSpecificationMethods());
            } finally {
                // Note. We need to make sure we capture and report all errors correctly.
                Mocker.verify();
            }
        }
    }

    private void runSpecificationMethod(final Object contextInstance, final LifecycleMethod specificationMethod) {
        methodValidator.checkMethodHasNoParameters(specificationMethod);
        methodInvoker.invokeMethod(contextInstance, specificationMethod.getMethod());
    }

    private void notifyListenersOfPreSpecification(final SpecificationMethod specificationMethod) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.preSpecificationMethod(specificationMethod);
        }
    }

    private void notifyListenersOfPostSpecification(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.postSpecificationMethod(specificationMethod, specificationResult);
        }
    }

    private void runMethods(final Object instance, final List<LifecycleMethod> methods) {
        methods.foreach(new Effect<LifecycleMethod>() {
            public void e(final LifecycleMethod method) {
                runMethod(instance, method);
            }
        });
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private void runMethod(final Object instance, final LifecycleMethod method) {
        methodValidator.checkMethodHasNoParameters(method);
        methodInvoker.invokeMethod(instance, method.getMethod());
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }

    private SpecificationResult createSpecResult(final LifecycleMethod specificationMethod, final SpecificationRunStatus runStatus,
            final long startTime) {
        final long executionTime = clock.getElapsedTime(startTime);
        return new SpecificationResultImpl(specificationMethod.getName(), runStatus, executionTime);
    }
}
