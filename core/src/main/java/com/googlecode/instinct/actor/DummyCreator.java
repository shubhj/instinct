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

package com.googlecode.instinct.actor;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import com.googlecode.instinct.internal.util.proxy.CgLibProxyGenerator;
import com.googlecode.instinct.internal.util.proxy.ProxyGenerator;
import java.lang.reflect.Array;
import static java.lang.reflect.Modifier.isFinal;
import net.sf.cglib.proxy.MethodInterceptor;

public final class DummyCreator implements SpecificationDoubleCreator {
    private final ProxyGenerator proxyGenerator = new CgLibProxyGenerator();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();

    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        checkNotNull(doubleType, roleName);
        if (doubleType.isArray()) {
            checkComponentTypeIsDummiable(doubleType, roleName);
            return createArrayFilledWithDummies(doubleType);
        } else {
            checkTypeIsDummiable(doubleType, roleName);
            return createDummy(doubleType);
        }
    }

    @SuppressWarnings({"unchecked"})
    private <T> T createArrayFilledWithDummies(final Class<T> doubleType) {
        final Class<?> componentType = doubleType.getComponentType();
        final Object arrayOfDummies = Array.newInstance(componentType, NUMBER_OF_DOUBLES_IN_AN_ARRAY);
        for (int i = 0; i < NUMBER_OF_DOUBLES_IN_AN_ARRAY; i++) {
            Array.set(arrayOfDummies, i, createDummy(componentType));
        }
        return (T) arrayOfDummies;
    }

    private <T> T createDummy(final Class<T> doubleType) {
        final MethodInterceptor methodInterceptor = objectFactory.create(DummyMethodInterceptor.class);
        return proxyGenerator.newProxy(doubleType, methodInterceptor);
    }

    private <T> void checkTypeIsDummiable(final Class<T> doubleType, final String roleName) {
        if (isNotDummiable(doubleType)) {
            final String message = "Unable to create a dummy " + doubleType.getName() + " (with role name '" + roleName +
                    "'). Dummy types cannot be primitives, final classes or enums, use a stub for these.";
            throw new SpecificationDoubleCreationException(message);
        }
    }

    private <T> void checkComponentTypeIsDummiable(final Class<T> doubleType, final String roleName) {
        final Class<?> componentType = doubleType.getComponentType();
        if (isNotDummiable(componentType)) {
            final String message = "Unable to create a dummy " + componentType.getName() + "[] (with role name '" + roleName +
                    "') as the component type is not itself dummiable. The component type cannot be a primitive, final class or " +
                    "enum, use a stub for these.";
            throw new SpecificationDoubleCreationException(message);
        }
    }

    private <T> boolean isNotDummiable(final Class<T> doubleType) {
        return doubleType.isEnum() || doubleType.isPrimitive() || isFinal(doubleType.getModifiers());
    }
}
