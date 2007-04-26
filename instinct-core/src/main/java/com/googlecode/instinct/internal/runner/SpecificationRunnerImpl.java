/*
 * Copyright 2006-2007 Tom Adams
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

import java.lang.reflect.Method;
import com.googlecode.instinct.internal.mock.MockVerifier;
import com.googlecode.instinct.internal.mock.MockVerifierImpl;
import com.googlecode.instinct.internal.mock.TestDoubleAutoWirer;
import com.googlecode.instinct.internal.mock.TestDoubleAutoWirerImpl;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.VERIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ConstructorInvoker;
import com.googlecode.instinct.internal.util.ConstructorInvokerImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Pass a spec method into a spec runner"})
public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final TestDoubleAutoWirer testDoubleAutoWirer = new TestDoubleAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private MethodInvoker methodInvoker = new MethodInvokerImpl();
    private LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final MockVerifier mockVerifier = new MockVerifierImpl();

    @Suggest({"Does each specification get it's own Mockery?", " How will this work if we want to allow manual mocking?",
            "Need access to the same statics",
            "Maybe pass in a BC class instantiation strategy, so that we can enable creating of only one instance of a BC, rather than one per spec"})
    public SpecificationResult run(final SpecificationContext context) {
        checkNotNull(context);
        return doRun(context);
    }

    // SUPPRESS IllegalCatch {
    @SuppressWarnings({"CatchGenericClass"})
    @Suggest("Make a clock wrapper that looks like org.jbehave.core.util.Timer.")
    private SpecificationResult doRun(final SpecificationContext specificationContext) {
        final long startTime = clock.getCurrentTime();
        try {
            final Object instance = invokeConstructor(specificationContext.getBehaviourContextClass());
            runSpecificationLifecycle(instance, specificationContext);
            return new SpecificationResultImpl(specificationContext.getSpecificationMethod().getName(), VERIFICATION_SUCCESS,
                    clock.getElapsedTime(startTime));
        } catch (Throwable e) {
            final SpecificationRunStatus status = new SpecificationRunFailureStatus(e);
            return new SpecificationResultImpl(specificationContext.getSpecificationMethod().getName(), status, clock.getElapsedTime(startTime));
        }
    }
    // } SUPPRESS IllegalCatch

    @Suggest("May need to stick verification of mocks in finally, if we report them as well as other errors.")
    private void runSpecificationLifecycle(final Object behaviourContext, final SpecificationContext specificationContext) {
        try {
            testDoubleAutoWirer.wire(behaviourContext);
            runMethods(behaviourContext, specificationContext.getBeforeSpecificationMethods());
            runMethod(behaviourContext, specificationContext.getSpecificationMethod());
            mockVerifier.verify(behaviourContext);
        } finally {
            runMethods(behaviourContext, specificationContext.getAfterSpecificationMethods());
        }
    }

    private void runMethods(final Object instance, final Method[] methods) {
        for (final Method method : methods) {
            runMethod(instance, method);
        }
    }

    private void runMethod(final Object instance, final Method specificationMethod) {
        methodValidator.checkMethodHasNoParameters(specificationMethod);
        methodInvoker.invokeMethod(instance, specificationMethod);
    }

    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }
}
