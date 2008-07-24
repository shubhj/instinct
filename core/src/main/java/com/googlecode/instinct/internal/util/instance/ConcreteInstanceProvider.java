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

package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.internal.util.Suggest;
import fj.data.List;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

// SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength|IllegalInstantiation {
@Suggest("THis should use the  triangulation provider, or boost random utilities.")
@SuppressWarnings(
        {"RawUseOfParameterizedType", "MagicNumber", "ReturnOfCollectionOrArrayField", "OverlyComplexMethod", "CachedNumberConstructorCall"})
public final class ConcreteInstanceProvider implements InstanceProvider {
    private static final Object OBJECT = new Object();
    private static final Object[] OBJECT_ARRAY = {OBJECT};
    private final InstanceProvider uberInstanceProvider = new GenericInstanceProvider();
    private final Objenesis objenesis = new ObjenesisStd();

    @SuppressWarnings({"unchecked"})
    public <T> T newInstance(final Class<T> cls) {
        if (cls.isEnum()) {
            return cls.getEnumConstants()[0];
        }
        if (cls.isAnnotation()) {
            return (T) AnAnnotation.class;
        }
        if (cls.isPrimitive()) {
            return (T) getPrimitiveInstance(cls);
        }
        if (cls.equals(Class.class)) {
            return (T) ArrayList.class;
        }
        if (cls.equals(Object.class)) {
            return (T) OBJECT;
        }
        if (cls.equals(Object[].class)) {
            return (T) OBJECT_ARRAY;
        }
        if (cls.equals(List.class)) {
            return (T) List.nil();
        }
        if (cls.equals(Long.class)) {
            return (T) new Long(1L);
        }
        if (cls.equals(Integer.class)) {
            return (T) new Integer(1);
        }
        if (cls.equals(String.class)) {
            return (T) "The quick brown fox jumps over the lazy dog.";
        }
        if (cls.equals(Field.class)) {
            return (T) createField();
        }
        if (cls.equals(Method.class)) {
            return (T) createMethod();
        }
        if (cls.isArray()) {
            return (T) createArray(cls.getComponentType());
        }
        return (T) createConcreteInstance(cls);
    }

    private <T> Object createArray(final Class<T> componentType) {
        final Object array = Array.newInstance(componentType, 1);
        Array.set(array, 0, uberInstanceProvider.newInstance(componentType));
        return array;
    }

    private Object getPrimitiveInstance(final Class type) {
        if (type == boolean.class) {
            return false;
        }
        if (type == byte.class) {
            return "x".getBytes()[0];
        }
        if (type == char.class) {
            return 'x';
        }
        if (type == short.class) {
            return 42;
        }
        if (type == int.class) {
            return 34177239;
        }
        if (type == long.class) {
            return 44762654617L;
        }
        if (type == float.class) {
            return 31.123475f;
        }
        if (type == double.class) {
            return 299.792458d;
        }
        throw new InstantiationException("Unable to create an instance of primitive type " + type);
    }

    private Object createConcreteInstance(final Class<?> implementationClass) {
        return objenesis.newInstance(implementationClass);
    }

    private Object createField() {
        try {
            return getClass().getDeclaredField("OBJECT");
        } catch (NoSuchFieldException e) {
            throw new InstantiationException("Unable to create an instance of type " + Field.class.getName(), e);
        }
    }

    private Object createMethod() {
        try {
            return ArrayList.class.getMethod("size");
        } catch (NoSuchMethodException e) {
            throw new InstantiationException("Unable to create an instance of type " + Method.class.getName(), e);
        }
    }

    private @interface AnAnnotation {
    }
}
// } SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength|IllegalInstantiation
