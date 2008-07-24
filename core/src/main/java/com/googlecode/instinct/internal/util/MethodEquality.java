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

package com.googlecode.instinct.internal.util;

import fj.F;
import fj.pre.Equal;
import static fj.pre.Equal.equal;
import java.lang.reflect.Method;

public final class MethodEquality {
    private MethodEquality() {
        throw new UnsupportedOperationException();
    }

    public static Equal<Method> methodEquals() {
        return equal(new F<Method, F<Method, Boolean>>() {
            public F<Method, Boolean> f(final Method method1) {
                return new F<Method, Boolean>() {
                    public Boolean f(final Method method2) {
                        return method1.equals(method2);
                    }
                };
            }
        });
    }

    public static Equal<Method> methodEqualsIgnoringDeclaringClass() {
        return equal(new F<Method, F<Method, Boolean>>() {
            public F<Method, Boolean> f(final Method method1) {
                return new F<Method, Boolean>() {
                    public Boolean f(final Method method2) {
                        return equalsIgnoringClass(method1, method2);
                    }
                };
            }
        });
    }

    // Note. Taken directly from Method.equals(), removing declaring declaring class checks. Horrible stuff...
    private static boolean equalsIgnoringClass(final Method method1, final Method method2) {
        if (method1.getName().equals(method2.getName())) {
            if (method2.getReturnType().equals(method1.getReturnType())) {
                final Class<?>[] paramTypes1 = method1.getParameterTypes();
                final Class<?>[] paramsTypes2 = method2.getParameterTypes();
                if (paramTypes1.length == paramsTypes2.length) {
                    for (int i = 0; i < paramTypes1.length; i++) {
                        if (paramTypes1[i] != paramsTypes2[i]) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
