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
import fj.F;
import fj.data.List;
import java.lang.reflect.Method;

public final class NamedMethodLocatorImpl implements NamedMethodLocator {
    private final SuperClassTraversingMethodLocator methodLocator = new SuperClassTraversingMethodLocatorImpl();

    public <T> List<Method> locate(final Class<T> cls, final NamingConvention namingConvention) {
        checkNotNull(cls, namingConvention);
        return methodLocator.locate(cls).filter(new F<Method, Boolean>() {
            public Boolean f(final Method method) {
                return method.getName().matches(namingConvention.getPattern());
            }
        });
    }
}
