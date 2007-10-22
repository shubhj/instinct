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
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.test.TestingException;

public final class Reflector {
    private static final MethodInvoker METHOD_INVOKER = new MethodInvokerImpl();

    private Reflector() {
        throw new UnsupportedOperationException();
    }

    public static void insertFieldValue(final Object instance, final String fieldName, final Object value) {
        checkNotNull(instance, value);
        checkNotWhitespace(fieldName);
        final Field field = getField(instance, fieldName);
        setValue(field, instance, value);
    }

    public static <T> void insertFieldValue(final Class<T> cls, final String fieldName, final Object value) {
        checkNotNull(cls, value);
        checkNotWhitespace(fieldName);
        final Field field = getFieldByName(cls, fieldName);
        setValue(field, cls, value);
    }

    public static <T> void insertFieldValue(final Object instance, final Class<T> valueType, final Object value) {
        checkNotNull(instance, valueType, value);
        setFieldValue(instance, valueType, value);
    }

    @SuppressWarnings({"unchecked"})
    @Fix("Refactor this sucker.")
    public static void insertFieldValueUsingInferredType(final Object instance, final Object value) {
        checkNotNull(instance, value);
        if (containsFieldOfType(instance.getClass(), value.getClass())) {
            setFieldValue(instance, value.getClass(), value);
        } else {
            setFieldValueUsingSuperType(instance, value);
        }
    }

    public static <T> Method getMethod(final Class<T> cls, final String methodName, final Class<?>... paramTypes) {
        checkNotNull(cls, paramTypes);
        checkNotWhitespace(methodName);
        try {
            return cls.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new TestingException(e);
        }
    }

    public static <T> Method getDeclaredMethod(final Class<T> cls, final String methodName, final Class<?>... paramTypes) {
        checkNotNull(cls, paramTypes);
        checkNotWhitespace(methodName);
        try {
            return cls.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new TestingException(e);
        }
    }

    public static Object invokeMethod(final Object instance, final String methodName, final Class<?>[] paramTypes, final Object... params) {
        if (paramTypes.length != params.length) {
            throw new TestingException("Paramter types array and values array must be the same length");
        }
        final Method method = getMethod(instance.getClass(), methodName, paramTypes);
        return METHOD_INVOKER.invokeMethod(instance, method, params);
    }

    public static <T> Field getFieldByName(final Class<T> cls, final String fieldName) {
        checkNotNull(cls);
        checkNotWhitespace(fieldName);
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new TestingException("Unable to find field '" + fieldName + "' on class " + cls.getSimpleName(), e);
        }
    }

    private static void setFieldValueUsingSuperType(final Object instance, final Object value) {
        if (containsFieldOfType(instance.getClass(), value.getClass().getSuperclass())) {
            setFieldValue(instance, value.getClass().getSuperclass(), value);
        } else {
            final boolean fieldSetUsingParentInterface = setFieldValueUsingParentInterface(instance, value);
            if (!fieldSetUsingParentInterface) {
                throw new TestingException("Unable to find field of type "
                        + value.getClass().getSimpleName() + " on class " + instance.getClass().getSimpleName());
            }
        }
    }

    private static boolean setFieldValueUsingParentInterface(final Object instance, final Object value) {
        for (final Class<?> implementedInterfaceType : value.getClass().getInterfaces()) {
            if (containsFieldOfType(instance.getClass(), implementedInterfaceType)) {
                setFieldValue(instance, implementedInterfaceType, value);
                return true;
            }
        }
        return false;
    }

    private static <T> void setFieldValue(final Object instance, final Class<T> targetFieldType, final Object value) {
        final Field field = getFieldByType(instance.getClass(), targetFieldType);
        setValue(field, instance, value);
    }

    private static <T, U> Field getFieldByType(final Class<T> cls, final Class<U> fieldType) {
        final Field[] fields = cls.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getType().equals(fieldType)) {
                return field;
            }
        }
        throw new TestingException("Unable to find field of type '" + fieldType.getSimpleName() + "' on class " + cls.getSimpleName());
    }

    private static <T, U> boolean containsFieldOfType(final Class<T> cls, final Class<U> targetType) {
        final Field[] fields = cls.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getType().equals(targetType)) {
                return true;
            }
        }
        return false;
    }

    private static Field getField(final Object instance, final String fieldName) {
        return getFieldByName(instance.getClass(), fieldName);
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
