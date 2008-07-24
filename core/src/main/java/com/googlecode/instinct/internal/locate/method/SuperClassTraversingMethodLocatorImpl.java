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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.internal.util.Fj.toFjList;
import fj.F;
import fj.data.List;
import static fj.data.List.nil;
import fj.pre.Equal;
import static fj.pre.Equal.equal;
import java.lang.reflect.Method;

public final class SuperClassTraversingMethodLocatorImpl implements SuperClassTraversingMethodLocator {
    public <T> List<Method> locate(final Class<T> cls) {
        if (cls != null) {
            return methods(cls).append(locate(cls.getSuperclass())).nub(methodEquals());
        } else {
            return nil();
        }
    }

    private Equal<Method> methodEquals() {
        return equal(new F<Method, F<Method, Boolean>>() {
            public F<Method, Boolean> f(final Method method1) {
                return new F<Method, Boolean>() {
                    public Boolean f(final Method method2) {
                        return methodEqualsSansDeclaringClass(method1, method2);
                    }
                };
            }
        });
    }

    // Note. Taken directly from Method.equals(), removing declaring declaring class checks. Horrible stuff...
    private boolean methodEqualsSansDeclaringClass(final Method method1, final Method method2) {
        if (method1.getName().equals(method2.getName())) {
            if (method2.getReturnType().equals(method1.getReturnType())) {
                final Class<?>[] params1 = method1.getParameterTypes();
                final Class<?>[] params2 = method2.getParameterTypes();
                if (params1.length == params2.length) {
                    for (int i = 0; i < params1.length; i++) {
                        if (params1[i] != params2[i]) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private <T> List<Method> methods(final Class<T> cls) {
        return toFjList(cls.getDeclaredMethods());
    }
}
