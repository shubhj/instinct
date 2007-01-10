package com.googlecode.instinct.test.instance;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.internal.util.Suggest;

public final class UberInstanceProvider implements InstanceProvider {
    private static final InstanceProvider MOCKING_INSTANCE_PROVIDER = new ProxiedInstanceProvider();
    private static final InstanceProvider CONCRETE_INSTANCE_PROVIDER = new ConcreteInstanceProvider();

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        try {
            if (canBeMocked(cls)) {
                return createMockInstance(cls);
            } else {
                return createConctreteInstance(cls);
            }
        } catch (IllegalArgumentException e) {
            // Note. IllegalArgumentException comes back from EasyMock when can't mock a class.
            return createConctreteInstance(cls);
        }
    }

    private Object createMockInstance(final Class<?> cls) {
        return MOCKING_INSTANCE_PROVIDER.newInstance(cls);
    }

    private Object createConctreteInstance(final Class<?> cls) {
        return CONCRETE_INSTANCE_PROVIDER.newInstance(cls);
    }

    @Suggest("May need to change if we turn this into proxied access")
    private <T> boolean canBeMocked(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return cls.isInterface() || !isFinal(modifiers) && hasPublicNullaryConstructor(cls);
    }

    private <T> boolean hasPublicNullaryConstructor(final Class<T> cls) {
        try {
            new DefaultEdgeClass().getConstructor(cls, new Class[]{});
            return true;
        } catch (EdgeException e) {
            return false;
        }
    }
}
