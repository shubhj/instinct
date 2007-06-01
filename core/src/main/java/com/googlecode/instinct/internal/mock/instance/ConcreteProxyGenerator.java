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

package com.googlecode.instinct.internal.mock.instance;

import com.googlecode.instinct.internal.util.Suggest;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public final class ConcreteProxyGenerator implements ProxyGenerator {
//    private static final Map<Class<?>, Object> PROXIES = synchronizedMap(new WeakHashMap<Class<?>, Object>());

    @Suggest("Provide the ability to proxy more than one type")
    public <T> Object newProxy(final Class<T> classToProxy, final MethodInterceptor methodInterceptor) {
        return createProxy(classToProxy, methodInterceptor);
    }

    @Suggest("Use JavaAssist and return an instumented class instead as we can't proxy Class.getDeclaredMethods()")
    private <T> Object createProxy(final Class<T> classToProxy, final MethodInterceptor methodInterceptor) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(classToProxy);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }
}
