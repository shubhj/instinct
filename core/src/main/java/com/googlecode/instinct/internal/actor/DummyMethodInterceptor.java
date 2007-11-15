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
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static net.sf.cglib.proxy.Enhancer.isEnhanced;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class DummyMethodInterceptor implements MethodInterceptor {
    @SuppressWarnings({"ProhibitedExceptionDeclared"})
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        checkNotNull(obj, method, args, proxy);
        if (isMethodInheritedFromObject(method) || isMethodOveriddenFromObject(method)) {
            return proxy.invokeSuper(obj, args);
        } else {
            return rejectAllNonObjectMethodCalls(obj, method);
        }
    }

    private Object rejectAllNonObjectMethodCalls(final Object obj, final Method method) {
        final String message = "Method " + method.getName() + "() was called on a dummy instance of " + getDummyClassName(obj) + ". "
                + "If you expect methods to be called on this double you should make it a mock or stub.";
        throw new IllegalInvocationException(message);
    }

    private String getDummyClassName(final Object obj) {
        if (isEnhanced(obj.getClass())) {
            return obj.getClass().getSuperclass().getName();
        } else {
            return obj.getClass().getName();
        }
    }

    private boolean isMethodInheritedFromObject(final Method method) {
        return method.getDeclaringClass().equals(Object.class);
    }

    private boolean isMethodOveriddenFromObject(final Method method) {
        return objectNoArgsMethod(method) || isObjectEqualsMethod(method);
    }

    private boolean objectNoArgsMethod(final Method method) {
        final String methodName = method.getName();
        return methodName.equals("clone") || methodName.equals("finalize") || methodName.equals("hashCode") || methodName.equals("toString");
    }

    private boolean isObjectEqualsMethod(final Method method) {
        final String methodName = method.getName();
        final Class<?>[] paramTypes = method.getParameterTypes();
        return methodName.equals("equals") && paramTypes.length == 1 && paramTypes[0].equals(Object.class);
    }
}
