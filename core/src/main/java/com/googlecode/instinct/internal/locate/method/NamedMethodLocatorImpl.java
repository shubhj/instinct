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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.naming.NamingConvention;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public final class NamedMethodLocatorImpl implements NamedMethodLocator {
    private final HierarchicalMethodLocator methodLocator = new HierarchicalMethodLocatorImpl();

    public <T> Collection<Method> locate(final Class<T> cls, final NamingConvention namingConvention) {
        checkNotNull(cls, namingConvention);
        final Collection<Method> locatedMethods = new ArrayList<Method>();
        for (final Method method : findMethods(cls)) {
            if (method.getName().matches(namingConvention.getPattern())) {
                locatedMethods.add(method);
            }
        }
        return locatedMethods;
    }

    private <T> Iterable<Method> findMethods(final Class<T> cls) {
        return methodLocator.locate(cls);
    }
}
