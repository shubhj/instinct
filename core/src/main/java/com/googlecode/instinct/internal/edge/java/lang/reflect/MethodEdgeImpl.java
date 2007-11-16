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

package com.googlecode.instinct.internal.edge.java.lang.reflect;

import au.net.netstorm.boost.edge.EdgeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MethodEdgeImpl implements MethodEdge {
    private final Method method;

    public MethodEdgeImpl(final Method method) {
        this.method = method;
    }

    public Object invoke(final Object obj, final Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new EdgeException(e);
        } catch (InvocationTargetException e) {
            throw new EdgeException(e);
        }
    }

    public String getName() {
        return method.getName();
    }
}
