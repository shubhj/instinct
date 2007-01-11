package com.googlecode.instinct.internal.mock.instance;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;

public final class UberInstanceProvider implements InstanceProvider {
    // Note. Needs to be static as is creates an instance of
    private static final InstanceProvider CONCRETE_INSTANCE_PROVIDER = new ConcreteInstanceProvider();
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final InstanceProvider mockingInstanceProvider = new ProxiedInstanceProvider();

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
        return mockingInstanceProvider.newInstance(cls);
    }

    private Object createConctreteInstance(final Class<?> cls) {
        return CONCRETE_INSTANCE_PROVIDER.newInstance(cls);
    }

    @Suggest("May need to change if we turn this into proxied access")
    private <T> boolean canBeMocked(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return cls.isInterface() || !isFinal(modifiers) && hasPublicNullaryConstructor(cls);
    }

    @Suggest("May be able to remove this is we get the new JMock class mock controller working.")
    private <T> boolean hasPublicNullaryConstructor(final Class<T> cls) {
        try {
            edgeClass.getConstructor(cls, new Class[]{});
            return true;
        } catch (EdgeException e) {
            return false;
        }
    }
}
