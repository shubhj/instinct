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

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdgeImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkIsConcreteClass;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import java.lang.reflect.Constructor;
import static java.util.Arrays.asList;
import java.util.Iterator;

@SuppressWarnings({"unchecked"})
public final class ObjectFactoryImpl implements ObjectFactory {
    private final ClassEdge edgeClass = new ClassEdgeImpl();
    private final ConstructorEdge edgeConstructor = new ConstructorEdgeImpl();
    private final PrimitiveTypeBoxer primitiveTypeBoxer = new PrimitiveTypeBoxerImpl();

    public <T> T create(final Class<T> concreteClass, final Object... constructorArgumentValues) {
        checkNotNull(concreteClass);
        checkIsConcreteClass(concreteClass);
        final Constructor<T> constructor = findConstructor(concreteClass, constructorArgumentValues);
        return instantiate(constructor, constructorArgumentValues);
    }

    public <T> T create(final Class<T> concreteClass, final Class<?>[] constructorArgumentTypes, final Object[] constructorArgumentValues) {
        checkNotNull(concreteClass, constructorArgumentTypes, constructorArgumentValues);
        checkIsConcreteClass(concreteClass);
        final Constructor<T> constructor = edgeClass.getConstructor(concreteClass, constructorArgumentTypes);
        return instantiate(constructor, constructorArgumentValues);
    }

    private <T> Constructor<T> findConstructor(final Class<T> cls, final Object... argumentValues) throws ObjectCreationException {
        final Constructor<?>[] constructors = cls.getDeclaredConstructors();
        for (final Constructor<?> constructor : constructors) {
            final Class<?>[] types = constructor.getParameterTypes();
            if (argumentValues.length == types.length && typesMatch(types, argumentValues)) {
                return (Constructor<T>) constructor;
            }
        }
        throw new ObjectCreationException(createFailureMessage(cls, argumentValues));
    }

    private <T> T instantiate(final Constructor<T> constructor, final Object... values) {
        try {
            return edgeConstructor.newInstance(constructor, values);
        } catch (EdgeException e) {
            throw new ObjectCreationException("Factory does not have permission to access constructor: " + constructor, e);
        }
    }

    private boolean typesMatch(final Class<?>[] constructorTypes, final Object... argumentValues) {
        for (int i = 0; i < constructorTypes.length; i++) {
            final Class<?> contructorType = boxPrimitive(constructorTypes[i]);
            if (argumentValues[i] != null && !contructorType.isAssignableFrom(argumentValues[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    private <T> Class<?> boxPrimitive(final Class<T> argumentType) {
        return argumentType.isPrimitive() ? primitiveTypeBoxer.boxPrimitiveType(argumentType) : argumentType;
    }

    private <T> String createFailureMessage(final Class<T> cls, final Object... argumentValues) {
        final StringBuilder builder = new StringBuilder();
        for (final Iterator<Object> iterator = asList(argumentValues).iterator(); iterator.hasNext();) {
            final Object o = iterator.next();
            builder.append(getClassName(o));
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }
        return cls.getSimpleName() + " does not contain a constructor with types: [" + builder + ']';
    }

    @Suggest("Make this proxy code less dependent on jMock proxies, what about java.lang.reflect.proxy? What about multiple interfaces.")
    private String getClassName(final Object object) {
        final Class<?> cls = object.getClass();
        final String simpleName = cls.getSimpleName();
        if (simpleName.startsWith("$Proxy")) {
            return cls.getInterfaces()[0] == null ? simpleName : cls.getInterfaces()[0].getSimpleName();
        }
        return simpleName;
    }
}
