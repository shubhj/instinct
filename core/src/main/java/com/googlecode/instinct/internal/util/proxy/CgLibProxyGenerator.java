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

package com.googlecode.instinct.internal.util.proxy;

import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import static java.lang.reflect.Modifier.isFinal;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public final class CgLibProxyGenerator implements ProxyGenerator {
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Objenesis objenesis = new ObjenesisStd();

    @SuppressWarnings({"unchecked"})
    public <T> T newProxy(final Class<T> typeToProxy, final MethodInterceptor methodInterceptor) {
        checkArguments(typeToProxy, methodInterceptor);
        final Class<T> proxyInstance = createProxiedClass(typeToProxy, methodInterceptor);
        final Object factory = instantiateProxiedType(proxyInstance);
        registerMethodInterceptor(factory, methodInterceptor);
        return (T) factory;
    }

    @SuppressWarnings({"unchecked"})
    private <T> Class<T> createProxiedClass(final Class<T> typeToProxy, final MethodInterceptor methodInterceptor) {
        final CgLibEnhancer enhancer = createEnhancer(typeToProxy);
        // Note. The NoOp class *must* be second in the list, as it's index corresponds to the index in IgnoreBridgeMethodsCallbackFilter.
        enhancer.setCallbackTypes(methodInterceptor.getClass(), NoOp.class);
        return (Class<T>) enhancer.createClass();
    }

    private <T> Object instantiateProxiedType(final Class<T> proxiedType) {
        return objenesis.newInstance(proxiedType);
    }

    private void registerMethodInterceptor(final Object proxyInstance, final MethodInterceptor methodInterceptor) {
        // Note. The NoOp class *must* be second in the list, as it's index corresponds to the index in IgnoreBridgeMethodsCallbackFilter.
        ((Factory) proxyInstance).setCallbacks(new Callback[]{methodInterceptor, NoOp.INSTANCE});
    }

    private <T> CgLibEnhancer createEnhancer(final Class<T> typeToProxy) {
        final CgLibEnhancer enhancer = objectFactory.create(CgLibEnhancerImpl.class);
        setSuperClass(enhancer, typeToProxy);
        enhancer.setCallbackFilter(new IgnoreBridgeMethodsCallbackFilter());
        if (typeToProxy.getSigners() != null) {
            enhancer.setNamingPolicy(new SignedClassSafeNamingPolicy());
        }
        return enhancer;
    }

    private <T> void setSuperClass(final CgLibEnhancer enhancer, final Class<T> typeToProxy) {
        if (typeToProxy.isInterface()) {
            enhancer.setSuperclass(Object.class);
            enhancer.setInterface(typeToProxy);
        } else {
            enhancer.setSuperclass(typeToProxy);
        }
    }

    private <T> void checkArguments(final Class<T> typeToProxy, final MethodInterceptor methodInterceptor) {
        checkNotNull(typeToProxy, methodInterceptor);
        checkNotFinal(typeToProxy);
    }

    private <T> void checkNotFinal(final Class<T> typeToProxy) {
        if (isFinal(typeToProxy.getModifiers())) {
            throw new IllegalArgumentException("Cannot proxy final class " + typeToProxy.getName());
        }
    }
}
