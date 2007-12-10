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

package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import junit.framework.Assert;

public final class MethodTestUtilImpl implements MethodTestUtil {
    public Object invoke(final Object invokee, final String methodName, final Object[] parameters) {
        final Method method = getMethod(invokee, methodName);
        return invoke(invokee, method, parameters);
    }

    public Class getThrowsType(final Method method) {
        final Class[] exceptions = method.getExceptionTypes();
        final String name = method.getName();
        if (exceptions.length != 1) {
            fail(name + "() must throw a single exception.");
        }
        return exceptions[0];
    }

    private Method getMethod(final Object instance, final String methodName) {
        final Class type = instance.getClass();
        return getMethod(type, methodName);
    }

    private Method getMethod(final Class type, final String methodName) {
        final Method[] methods = type.getMethods();
        for (final Method method : methods) {
            if (matches(method, methodName)) {
                return method;
            }
        }
        throw new IllegalStateException("No method " + methodName + " on " + type);
    }

    private Object invoke(final Object invokee, final Method method, final Object[] parameters) {
        method.setAccessible(true);
        final MethodEdge methodEdge = new MethodEdgeImpl(method);
        return methodEdge.invoke(method, invokee, parameters);
    }

    private boolean matches(final Member method, final Object methodName) {
        final String name = method.getName();
        return name.equals(methodName);
    }

    private void fail(final String msg) {
        Assert.fail(msg);
    }
}
