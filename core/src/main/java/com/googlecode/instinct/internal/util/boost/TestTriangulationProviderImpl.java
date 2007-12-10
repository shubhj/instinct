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

package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.edge.java.lang.reflect.ProxyEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ProxyEdgeImpl;
import com.googlecode.instinct.internal.util.PrimitiveTypeBoxer;
import com.googlecode.instinct.internal.util.PrimitiveTypeBoxerImpl;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;

public final class TestTriangulationProviderImpl implements TestTriangulationProvider {
    private static final InvocationHandler NO_OP_INVOCATION_HANDLER = new NoOpInvocationHandler();
    private static final int ARRAY_LENGTH = 5;
    private ProxyEdge edgeProxy = new ProxyEdgeImpl();
    private ProxyFactory proxyFactory = new ProxyFactoryImpl(edgeProxy);
    private PrimitiveBoxer primitiveBoxer = new PrimitiveBoxerImpl();
    private RandomProvider randomProvider = new RandomProviderImpl();
    private final PrimitiveTypeBoxer primitiveTypeBoxer = new PrimitiveTypeBoxerImpl();

    public Object getInstance(final Class type) {
        if (type.isInterface()) {
            return randomInterface(type);
        }
        if (type.isArray()) {
            return randomArray(type);
        }
        if (isPrimitive(type)) {
            return randomPrimitiveType(type);
        }
        return randomJavaType(type);
    }

    public Object[] getInstances(final Class[] types) {
        final Object[] params = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            params[i] = getInstance(types[i]);
        }
        return params;
    }

    private Object randomPrimitiveType(final Class type) {
        final Class boxed = primitiveTypeBoxer.boxPrimitiveType(type);
        return randomJavaType(boxed);
    }

    private Object randomInterface(final Class type) {
        final Interface iface = new InterfaceImpl(type);
        return proxyFactory.newProxy(iface, NO_OP_INVOCATION_HANDLER);
    }

    private Object randomArray(final Class type) {
        final Class componentType = type.getComponentType();
        final Object array = Array.newInstance(componentType, ARRAY_LENGTH);
        populate(array, componentType);
        return array;
    }

    private boolean isPrimitive(final Class type) {
        return primitiveBoxer.isPrimitive(type);
    }

    private Object randomJavaType(final Class type) {
        return randomProvider.getRandom(type);
    }

    private void populate(final Object array, final Class type) {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            final Object instance = getInstance(type);
            Array.set(array, i, instance);
        }
    }
}
