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
package com.googlecode.instinct.sandbox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

final class MethodImplementer {

    private Object object;
    private Method method;

    public MethodImplementer(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke(Object... params) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(object, params);
    }

    public boolean hasMoreSpecificReturnTypeThan(MethodImplementer other) {
        final Class otherReturnType = other.getMethod().getReturnType();
        return otherReturnType.isAssignableFrom(method.getReturnType());
    }

    public boolean hasMethodWithSameNameAndParametersAs(Method targetMethod) {
        return sameNames(method, targetMethod)
                && sameParameters(method.getParameterTypes(), targetMethod.getParameterTypes());
    }

    private boolean sameNames(Method targetMethod, Method method) {
        return targetMethod.getName().equals(method.getName());
    }

    private static boolean sameParameters(Class[] types1, Class[] types2) {
        return Arrays.equals(types1, types2);
    }

    public String toString() {
        return method.toGenericString();
    }
}
