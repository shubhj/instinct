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

package com.googlecode.instinct.internal.actor;

import java.lang.reflect.Method;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getDeclaredMethod;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.jmock.Expectations;

public final class DummyMethodInterceptorAtomicTest extends InstinctTestCase {
    @Subject(implementation = DummyMethodInterceptor.class) private MethodInterceptor methodInterceptor;
    @Mock private MethodProxy methodProxy;
    @Dummy private Object obj;
    @Dummy private Method method;
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
        try {
            methodInterceptor.intercept(obj, method, args, methodProxy);
            fail("Expected IllegalInvocationException thrown");
        } catch (Throwable t) {
            expect.that(t).instanceOf(IllegalInvocationException.class);
            expect.that(t.getMessage()).equalTo("Method size() was called on a dummy instance of class " + obj.getClass().getName() + ". " +
                    "If you expect methods to be called on this double you should make it a mock or stub.");
        }
    }

    private void checkPassesObjectMethodsToSuperClass(final String methodName, final Class<?>... paramTypes) throws Throwable {
        expect.that(new Expectations() {
            {
                one(methodProxy).invokeSuper(obj, args);
            }
        });
        methodInterceptor.intercept(obj, getDeclaredMethod(Object.class, methodName, paramTypes), args, methodProxy);
    }
}
