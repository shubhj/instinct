package com.googlecode.instinct.internal.mock.instance;

import java.lang.reflect.Array;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import org.easymock.classextension.internal.ClassInstantiatorFactory;

// DEBT CyclomaticComplexity|NPathComplexity|MethodLength {
@SuppressWarnings({"RawUseOfParameterizedType", "MagicNumber"})
public final class ConcreteInstanceProvider implements InstanceProvider {
    private static final Object OBJECT = new Object();
    private static final Object[] OBJECT_ARRAY = {OBJECT};
    private final InstanceProvider uberInstanceProvider = new UberInstanceProvider();
//    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

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
        throw new UnsupportedOperationException("Unable to return an instance of type " + type + " (please write the code)");
    }

    @Suggest({"Now bound to EasyMock! Remove this dependency", "Or, replace this class altogether with just a dependency on easymock class ext"})
    private Object createConcreteInstance(final Class<?> implementationClass) {
        try {
            return ClassInstantiatorFactory.getInstantiator().newInstance(implementationClass);
        } catch (java.lang.InstantiationException e) {
            throw new InstantiationException(e);
        }
//        final Constructor<?> constructor = getConstructors(implementationClass)[0];
//        final Object[] parameterValues = createParameterValues(constructor.getParameterTypes());
//        return edgeConstructor.newInstance(constructor, parameterValues);
    }

//    @Suggest("Do we need to find all constructors? use getDeclaredConstructors() instead.")
//    private Constructor[] getConstructors(final Class<?> cls) {
//        final Constructor[] constructors = cls.getConstructors();
//        if (constructors.length == 0) {
//            throw new IllegalArgumentException("Class " + cls.getSimpleName() + " does not have a public constructor");
//        }
//        return constructors;
//    }

//    private Object[] createParameterValues(final Class<?>[] paramTypes) {
//        final Object[] paramValues = new Object[paramTypes.length];
//        for (int i = 0; i < paramTypes.length; i++) {
//            paramValues[i] = uberInstanceProvider.newInstance(paramTypes[i]);
//        }
//        return paramValues;
//    }
}
// } DEBT CyclomaticComplexity|NPathComplexity|MethodLength
