package com.googlecode.instinct.test.instance;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;

@SuppressWarnings({"RawUseOfParameterizedType", "MagicNumber"})
public final class ConcreteInstanceProvider implements InstanceProvider {
    private static final Object OBJECT = new Object();
    private static final Object[] OBJECT_ARRAY = {OBJECT};
//    private final InstanceProvider uberInstanceProvider = new com.yoogalu.gift.test.instance.UberInstanceProvider();
    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

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
        if (cls.isArray()) {
            return createArray(cls.getComponentType());
        }
        return createConcreteInstance(cls);
    }

    private Object createArray(final Class componentType) {
        final Object array = Array.newInstance(componentType, 1);
//        Array.set(array, 0, uberInstanceProvider.newInstance(componentType));
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
        throw new UnsupportedOperationException("Please write the code for primitive type: " + type);
    }

    private Object createConcreteInstance(final Class<?> implementationClass) {
        final Constructor<?> constructor = getConstructors(implementationClass)[0];
        final Object[] parameterValues = createParameterValues(constructor.getParameterTypes());
        return edgeConstructor.newInstance(constructor, parameterValues);
    }

    private Constructor[] getConstructors(final Class<?> cls) {
        final Constructor[] constructors = cls.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("Class " + cls.getSimpleName() + " does not have a public constructor");
        }
        return constructors;
    }

    private Object[] createParameterValues(final Class<?>[] paramTypes) {
        final Object[] paramValues = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
//            paramValues[i] = uberInstanceProvider.newInstance(paramTypes[i]);
        }
        return paramValues;
    }
}
