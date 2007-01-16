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
import com.googlecode.instinct.internal.testdouble.TestDoubleAutoWirer;
import com.googlecode.instinct.internal.testdouble.TestDoubleAutoWirerImpl;
import com.googlecode.instinct.internal.util.ConstructorInvoker;
import com.googlecode.instinct.internal.util.ConstructorInvokerImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private MethodInvoker methodInvoker = new MethodInvokerImpl();
    private LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final TestDoubleAutoWirer testDoubleAutoWirer = new TestDoubleAutoWirerImpl();
    private final MockVerifier mockVerifier = new MockVerifierImpl();

    public void run(final SpecificationContext context) {
        checkNotNull(context);
        final Object instance = invokeConstructor(context.getBehaviourContextClass());
        try {
            testDoubleAutoWirer.wire(instance);
            runMethods(instance, context.getBeforeSpecificationMethods());
            attemptToInvoke(instance, context.getSpecificationMethod());
            mockVerifier.verify(instance);
        } finally {
            runMethods(instance, context.getAfterSpecificationMethods());
        }
    }

    private void runMethods(final Object instance, final Method[] methods) {
        for (final Method method : methods) {
            attemptToInvoke(instance, method);
        }
    }

    private void attemptToInvoke(final Object instance, final Method specificationMethod) {
        checkMethod(specificationMethod);
        methodInvoker.invokeMethod(instance, specificationMethod);
    }

    private void checkMethod(final Method method) {
        methodValidator.checkMethodHasNoReturnType(method);
        methodValidator.checkMethodHasNoParameters(method);
    }

    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }
}
