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

import java.lang.reflect.Constructor;
import static java.util.Arrays.asList;
import java.util.Iterator;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import static com.googlecode.instinct.internal.util.ParamChecker.checkIsConcreteClass;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

@SuppressWarnings({"unchecked"})
public final class ObjectFactoryImpl implements ObjectFactory {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

    public <T> T create(final Class<T> concreteClass, final Object... constructorArgumentValues) {
        checkNotNull(concreteClass, constructorArgumentValues);
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
        final Constructor<T>[] constructors = cls.getDeclaredConstructors();
        for (final Constructor<T> constructor : constructors) {
            final Class<?>[] types = constructor.getParameterTypes();
            if (argumentValues.length == types.length && typesMatch(types, argumentValues)) {
                return constructor;
            }
        }
        throw new ObjectCreationException(createFailureMessage(cls, argumentValues));
    }

    private <T> T instantiate(final Constructor<T> constructor, final Object... values) {
        try {
            return (T) edgeConstructor.newInstance(constructor, values);
        } catch (EdgeException e) {
            throw new ObjectCreationException("Factory does not have permission to access constructor: " + constructor, e);
        }
    }

    private boolean typesMatch(final Class<?>[] types, final Object... values) {
        for (int i = 0; i < values.length; i++) {
            if (!types[i].isAssignableFrom(values[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    private <T> String createFailureMessage(final Class<T> cls, final Object... argumentValues) {
        final StringBuilder builder = new StringBuilder();
        for (final Iterator<Object> iterator = asList(argumentValues).iterator(); iterator.hasNext();) {
            builder.append(iterator.next().getClass());
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }
        return cls.getSimpleName() + " does not contain a constructor with types: [" + builder + ']';
    }
}
