package au.id.adams.instinct.test.instance;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;

public final class UberInstanceProvider implements InstanceProvider {
    private static final InstanceProvider MOCKING_INSTANCE_PROVIDER = new ProxiedInstanceProvider();
    private static final InstanceProvider CONCRETE_INSTANCE_PROVIDER = new ConcreteInstanceProvider();

    @SuppressWarnings({"RawUseOfParameterizedType"})
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

    private boolean canBeMocked(final Class<?> cls) {
        final int modifiers = cls.getModifiers();
        return cls.isInterface() || !isFinal(modifiers);
    }
}
