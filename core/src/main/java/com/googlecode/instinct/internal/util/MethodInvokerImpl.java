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

import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import java.lang.reflect.Method;

@Suggest("Test this.")
public final class MethodInvokerImpl implements MethodInvoker {
    private static final Object[] WITH_NO_PARAMS = {};

    public Object invokeMethod(final Object instance, final Method method) {
        return invokeMethod(instance, method, WITH_NO_PARAMS);
    }

    public Object invokeMethod(final Object instance, final Method method, final Object... params) {
        method.setAccessible(true);
        return new MethodEdgeImpl(method).invoke(instance, params);
    }
}
