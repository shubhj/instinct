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

package com.googlecode.instinct.internal.util;

import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeMethod;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeMethod;

@Fix("Use the instinct MethodEdge.")
public final class MethodInvokerImpl implements MethodInvoker {
    private static final Object[] NO_PARAMS = {};
    private final EdgeMethod edgeMethod = new DefaultEdgeMethod();

    public void invokeMethod(final Object instance, final Method method) {
        method.setAccessible(true);
        edgeMethod.invoke(method, instance, NO_PARAMS);
    }
}
