package com.googlecode.instinct.test.reflect;

import java.lang.reflect.Field;
import com.googlecode.instinct.test.TestingException;

public final class ReflectUtil {
    private ReflectUtil() {
        throw new UnsupportedOperationException();
    }

    public static void insertFieldValue(final Object instance, final String fieldName, final Object value) {
        final Field field = getField(instance, fieldName);
        setValue(field, instance, value);
    }

    private static Field getField(final Object instance, final String fieldName) {
        try {
            return instance.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new TestingException("Unable to find field '" + fieldName + "' on class " + instance.getClass(), e);
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
