/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.actor;

import java.lang.reflect.Method;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import org.jmock.Expectations;

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class DummyMethodInterceptorAtomicTest extends InstinctTestCase {
    @Subject(implementation = DummyMethodInterceptor.class) private MethodInterceptor methodInterceptor;
    @Mock private MethodProxy methodProxy;
    @Stub private Object obj;
    @Stub private OveriddenObjectMethods overiddenObjectMethods;
    @Stub private Method method;
    @Dummy private Object[] args;

    public void testConformsToClassTraits() {
        checkClass(DummyMethodInterceptor.class, MethodInterceptor.class);
    }

    public void testPassesAllObjectMethodsThroughToSuperClass() throws Throwable {
        checkPassesObjectMethodsToSuperClass("clone");
        checkPassesObjectMethodsToSuperClass("equals", Object.class);
        checkPassesObjectMethodsToSuperClass("finalize");
        checkPassesObjectMethodsToSuperClass("getClass");
        checkPassesObjectMethodsToSuperClass("hashCode");
        checkPassesObjectMethodsToSuperClass("notify");
        checkPassesObjectMethodsToSuperClass("notifyAll");
        checkPassesObjectMethodsToSuperClass("toString");
        checkPassesObjectMethodsToSuperClass("wait");
        checkPassesObjectMethodsToSuperClass("wait", long.class);
        checkPassesObjectMethodsToSuperClass("wait", long.class, int.class);
    }

    public void testThrowsExceptionsOnAllMethodNonObjectCalls() {
        final Object instanceToIntercept = obj;
        final String expectedClassName = instanceToIntercept.getClass().getName();
        expectIllegalInvocationThrown(instanceToIntercept, expectedClassName);
    }

    public void testThrowsExceptionsOnAllMethodNonObjectCallsUsingCorrectTypeForCgLibEnhancedClasses() throws Exception {
        final Enhancer enhancer = new Enhancer();
        enhancer.setCallbackType(NoOp.class);
        enhancer.setSuperclass(Object.class);
        final Object enhancedObject = enhancer.createClass().newInstance();
        expectIllegalInvocationThrown(enhancedObject, enhancedObject.getClass().getSuperclass().getName());
    }

    public void testPassesAllOveriddenObjectMethodsThroughToSuperclass() throws Throwable {
        checkPassesObjectMethodsToSuperClass(overiddenObjectMethods, "clone");
        checkPassesObjectMethodsToSuperClass(overiddenObjectMethods, "equals", Object.class);
        checkPassesObjectMethodsToSuperClass(overiddenObjectMethods, "finalize");
        checkPassesObjectMethodsToSuperClass(overiddenObjectMethods, "hashCode");
        checkPassesObjectMethodsToSuperClass(overiddenObjectMethods, "toString");
    }

    // Note. Can't use ExceptionTestChecker as it doesn't handle checked exceptions.
    private void expectIllegalInvocationThrown(final Object instanceToIntercept, final String expectedClassName) {
        try {
            methodInterceptor.intercept(instanceToIntercept, method, args, methodProxy);
            fail("Expected IllegalInvocationException to be thrown");
        } catch (Throwable t) {
            expect.that(t).isAnInstanceOf(IllegalInvocationException.class);
            expect.that(t.getMessage()).isEqualTo("Method " + method.getName() + "() was called on a dummy instance of " + expectedClassName + ". " +
                    "If you expect methods to be called on this specification double you should make it a mock or stub.");
        }
    }

    private void checkPassesObjectMethodsToSuperClass(final String methodName, final Class<?>... paramTypes) throws Throwable {
        checkPassesObjectMethodsToSuperClass(obj, methodName, paramTypes);
    }

    private void checkPassesObjectMethodsToSuperClass(
            final Object proxiedObject, final String methodName, final Class<?>... paramTypes) throws Throwable {
        expect.that(new Expectations() {
            {
                one(methodProxy).invokeSuper(proxiedObject, args);
            }
        });
        methodInterceptor.intercept(proxiedObject, getDeclaredMethod(proxiedObject.getClass(), methodName, paramTypes), args, methodProxy);
    }

    @SuppressWarnings({"FinalizeDeclaration", "CloneInNonCloneableClass", "ProhibitedExceptionDeclared"})
    private static final class OveriddenObjectMethods {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }

        @Override
        public boolean equals(final Object obj) {
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
