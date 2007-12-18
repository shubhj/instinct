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

import com.googlecode.instinct.internal.util.instance.PrimitiveTypeBoxer;
import com.googlecode.instinct.internal.util.instance.PrimitiveTypeBoxerImpl;
import com.googlecode.instinct.internal.util.proxy.CgLibProxyGenerator;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@SuppressWarnings({"unchecked"})
public final class TestTriangulationProviderImpl implements TestTriangulationProvider {
    private static final int ARRAY_LENGTH = 5;
    private RandomProvider randomProvider = new RandomProviderImpl();
    private final PrimitiveTypeBoxer primitiveTypeBoxer = new PrimitiveTypeBoxerImpl();
    private final CgLibProxyGenerator proxyGenerator = new CgLibProxyGenerator();

    public <T> T getInstance(final Class<T> type) {
        if (type.isInterface()) {
            return randomInterface(type);
        }
        if (type.isArray()) {
            return randomArray(type);
        }
        if (type.isPrimitive()) {
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

    private <T> T randomPrimitiveType(final Class<T> type) {
        final Class<T> boxed = primitiveTypeBoxer.boxPrimitiveType(type);
        return randomJavaType(boxed);
    }

    private <T> T randomInterface(final Class<T> type) {
        return proxyGenerator.newProxy(type, new NoOpMethodInterceptor());
    }

    private <T> T randomArray(final Class<T> type) {
        final Class<?> componentType = type.getComponentType();
        final Object array = Array.newInstance(componentType, ARRAY_LENGTH);
        populate(array, componentType);
        return (T) array;
    }

    private <T> T randomJavaType(final Class<T> type) {
        return (T) randomProvider.getRandom(type);
    }

    private <T> void populate(final Object array, final Class<T> type) {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            final Object instance = getInstance(type);
            Array.set(array, i, instance);
        }
    }

    private static final class NoOpMethodInterceptor implements MethodInterceptor {
        public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) {
            return null;
        }
    }
}
