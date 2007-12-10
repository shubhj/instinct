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
import java.lang.reflect.InvocationHandler;

public final class ProxyFactoryImpl implements ProxyFactory {
    private final ProxyEdge delegate;

    public ProxyFactoryImpl(final ProxyEdge delegate) {
        this.delegate = delegate;
    }

    public Object newProxy(final Interface type, final InvocationHandler handler) {
        final Interface[] types = {type};
        return newProxy(types, handler);
    }

    public Object newProxy(final Interface[] types, final InvocationHandler handler) {
        final ClassLoader classloader = getClassLoader();
        final Class[] ifaces = toClasses(types);
        return delegate.getProxy(classloader, ifaces, handler);
    }

    private Class[] toClasses(final Interface[] types) {
        final int length = types.length;
        final Class[] result = new Class[length];
        for (int i = 0; i < length; i++) {
            result[i] = types[i].getType();
        }
        return result;
    }

    private ClassLoader getClassLoader() {
        final Class cls = getClass();
        return cls.getClassLoader();
    }
}
