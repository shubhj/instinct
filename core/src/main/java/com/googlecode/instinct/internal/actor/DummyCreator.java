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

package com.googlecode.instinct.internal.actor;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.ConcreteInstanceProvider;
import com.googlecode.instinct.internal.util.proxy.CgLibProxyGenerator;
import com.googlecode.instinct.internal.util.proxy.ProxyGenerator;
import net.sf.cglib.proxy.MethodInterceptor;

public final class DummyCreator implements SpecificationDoubleCreator {
    private final InstanceProvider concreteInstanceProvider = new ConcreteInstanceProvider();
    private final ProxyGenerator proxyGenerator = new CgLibProxyGenerator();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();

    @Suggest("Fill dummy arrays with dummies ala mock creator.")
    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        checkNotNull(doubleType, roleName);
        if (isFinalClass(doubleType)) {
            return createInstanceForType(doubleType);
        } else {
            return createProxyForType(doubleType);
        }
    }

    @SuppressWarnings({"unchecked"})
    private <T> T createInstanceForType(final Class<T> doubleType) {
        return (T) concreteInstanceProvider.newInstance(doubleType);
    }

    private <T> T createProxyForType(final Class<T> doubleType) {
        final MethodInterceptor methodInterceptor = objectFactory.create(DummyMethodInterceptor.class);
        return proxyGenerator.newProxy(doubleType, methodInterceptor);
    }

    private <T> boolean isFinalClass(final Class<T> doubleType) {
        return isFinal(doubleType.getModifiers());
    }
}
