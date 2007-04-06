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

package com.googlecode.instinct.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.test.TestingException;

public final class Reflector {
    private Reflector() {
        throw new UnsupportedOperationException();
    }

    public static void insertFieldValue(final Object instance, final String fieldName, final Object value) {
        checkNotNull(instance, fieldName, value);
        final Field field = getField(instance, fieldName);
        setValue(field, instance, v3alue);
    }

    public static <T> void insertFieldValue(final Object instance, final Class<T> valueType, final Object value) {
        checkNotNull(instance, value);
        final Field field = getField(instance.getClass(), valueType);
        setValue(field, instance, value);
    }

    private static <T, U> Field getField(final Class<T> cls, final Class<U> fieldClass) {
        final Field[] declaredFields = cls.getDeclaredFields();
        for (final Field declaredField : declaredFields) {
            if (declaredField.getType().equals(fieldClass)) {
                return declaredField;
            }
        }
        throw new TestingException("Unable to find field of type '" + fieldClass.getName() +
                "' on class " + cls.getName());
    }

    public static <T> Method getMethod(final Class<T> cls, final String methodName, final Class<?>... paramTypes) {
        checkNotNull(cls, methodName, paramTypes);
        try {
            return cls.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new TestingException(e);
        }
    }

    public static <T> Field getField(final Class<T> cls, final String fieldName) {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new TestingException("Unable to find field '" + fieldName + "' on class " + cls, e);
        }
    }

    private static Field getField(final Object instance, final String fieldName) {
        return getField(instance.getClass(), fieldName);
    }

    private static void setValue(final Field field, final Object instance, final Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new TestingException(e);
        }
    }
}
