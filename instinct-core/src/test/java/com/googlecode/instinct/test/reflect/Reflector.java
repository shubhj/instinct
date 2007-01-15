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
        setValue(field, instance, value);
    }

    public static <T> Method getMethod(final Class<T> cls, final String methodName, final Class<?>... paramTypes) {
        checkNotNull(cls, methodName, paramTypes);
        try {
            return cls.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new TestingException(e);
        }
    }

    public static Field getField(final Object instance, final String fieldName) {
        return getField(instance.getClass(), fieldName);
    }

    public static <T> Field getField(final Class<T> cls, final String fieldName) {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new TestingException("Unable to find field '" + fieldName + "' on class " + cls, e);
        }
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
