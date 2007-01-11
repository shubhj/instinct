package com.googlecode.instinct.internal.mock.instance;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;

public final class UberInstanceProvider implements InstanceProvider {
    // Note. Needs to be static as the concrete provider creates an instance of this class, and blows the stack.
    private static final InstanceProvider CONCRETE_INSTANCE_PROVIDER = new ConcreteInstanceProvider();
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final InstanceProvider mockingInstanceProvider = new MockInstanceProvider();

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
//        try {
        if (canBeMocked(cls)) {
            return createMockInstance(cls);
        } else {
            return createConctreteInstance(cls);
        }
//        } catch (IllegalArgumentException e) {
        // Note. IllegalArgumentException comes back from EasyMock when can't mock a class.
//            return createConctreteInstance(cls);
//        }
    }

    private Object createMockInstance(final Class<?> cls) {
        return mockingInstanceProvider.newInstance(cls);
    }

    private Object createConctreteInstance(final Class<?> cls) {
        return CONCRETE_INSTANCE_PROVIDER.newInstance(cls);
    }

    private <T> boolean canBeMocked(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        // && hasPublicNullaryConstructor(cls);
        return !isFinal(modifiers) || cls.isInterface();
    }

//    @Suggest("May be able to remove this is we get the new JMock class mock controller working.")
//    private <T> boolean hasPublicNullaryConstructor(final Class<T> cls) {
//        try {
//            edgeClass.getConstructor(cls, new Class[]{});
//            return true;
//        } catch (EdgeException e) {
//            return false;
//        }
//    }
}
