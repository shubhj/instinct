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

package com.googlecode.instinct.internal.instance;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import com.googlecode.instinct.test.reflect.Reflector;

// SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength {
@SuppressWarnings({"RawUseOfParameterizedType", "MagicNumber", "ReturnOfCollectionOrArrayField"})
public final class ConcreteInstanceProvider implements InstanceProvider {
    private static final Object OBJECT = new Object();
    private static final Object[] OBJECT_ARRAY = {OBJECT};
    private final InstanceProvider uberInstanceProvider = new UberInstanceProvider();
    private final Objenesis objenesis = new ObjenesisStd();

    public Object newInstance(final Class cls) {
        if (cls.isEnum()) {
            return cls.getEnumConstants()[0];
        }
        if (cls.isPrimitive()) {
            return getPrimitiveInstance(cls);
        }
        if (cls.equals(Class.class)) {
            return Class.class;
        }
        if (cls.equals(Object.class)) {
            return OBJECT;
        }
        if (cls.equals(Object[].class)) {
            return OBJECT_ARRAY;
        }
        if (cls.equals(Long.class)) {
            return 1L;
        }
        if (cls.equals(Integer.class)) {
            return 1;
        }
        if (cls.equals(String.class)) {
            return "The quick brown fox jumps over the lazy dog.";
        }
        if (cls.equals(Field.class)) {
            return Reflector.getFieldByName(getClass(), "OBJECT");
        }
        if (cls.isArray()) {
            return createArray(cls.getComponentType());
        }
        return createConcreteInstance(cls);
    }

    private Object createArray(final Class componentType) {
        final Object array = Array.newInstance(componentType, 1);
        Array.set(array, 0, uberInstanceProvider.newInstance(componentType));
        return array;
    }

    private Object getPrimitiveInstance(final Class type) {
        if (type == int.class) {
            return 34177239;
        }
        if (type == long.class) {
            return 44762654617L;
        }
        if (type == float.class) {
            return 31.123475f;
        }
        if (type == char.class) {
            return 'x';
        }
        if (type == byte.class) {
            return "x".getBytes()[0];
        }
        if (type == boolean.class) {
            return false;
        }
        throw new InstantiationException("Unable to return an instance of primitive type " + type);
    }

    private Object createConcreteInstance(final Class<?> implementationClass) {
        return objenesis.newInstance(implementationClass);
    }
}
// } SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength
