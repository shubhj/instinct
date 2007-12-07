/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import com.googlecode.instinct.internal.util.ParamChecker;
import com.googlecode.instinct.internal.util.Suggest;

import java.lang.reflect.Method;

@Suggest("Test drive this.")
public final class TheoryMethodInvokerImpl implements MethodInvoker {
    private final ObjectFactory factory = new ObjectFactoryImpl();

    // This method should never get called.
    public Object invokeMethod(final Object instance, final Method method) {
        ParamChecker.checkNotNull(instance, method);
        throw new NotAValidTheoryMethodException();
    }

    public Object invokeMethod(final Object instance, final Method method, final Object... params) {
        ParamChecker.checkNotNull(instance, method, params);
        final MethodEdge methodEdge = factory.create(MethodEdgeImpl.class, method);
        Object object = null;
        for (int i = 0; i < 5; i++) {
            object = methodEdge.invoke(instance, method, 5);
        }
        return object;
    }
}
