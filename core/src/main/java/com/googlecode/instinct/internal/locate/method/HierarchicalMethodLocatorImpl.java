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

import com.googlecode.instinct.internal.util.Suggest;
import java.lang.reflect.Method;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class HierarchicalMethodLocatorImpl implements HierarchicalMethodLocator {
    @Suggest("Make this immutable, all vars final")
    public <T> Set<Method> locate(final Class<T> cls) {
        final Set<Method> methods = new HashSet<Method>();
        methods.addAll(getAllMethodsForClass(cls));
        Class<?> superClass = cls.getSuperclass();
        while (superClass != null) {
            methods.addAll(getAllMethodsForClass(superClass));
            superClass = superClass.getSuperclass();
        }
        return methods;
    }

    private <T> Collection<Method> getAllMethodsForClass(final Class<T> cls) {
        return asList(cls.getDeclaredMethods());
    }
}
