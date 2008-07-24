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
import static com.googlecode.instinct.internal.util.MethodEquality.methodEqualsIgnoringDeclaringClass;
import fj.data.List;
import static fj.data.List.nil;
import java.lang.reflect.Method;

public final class SuperClassTraversingMethodLocatorImpl implements SuperClassTraversingMethodLocator {
    public <T> List<Method> locate(final Class<T> cls) {
        if (cls != null) {
            return methods(cls).append(locate(cls.getSuperclass())).nub(methodEqualsIgnoringDeclaringClass());
        } else {
            return nil();
        }
    }

    private <T> List<Method> methods(final Class<T> cls) {
        return toFjList(cls.getDeclaredMethods());
    }
}
