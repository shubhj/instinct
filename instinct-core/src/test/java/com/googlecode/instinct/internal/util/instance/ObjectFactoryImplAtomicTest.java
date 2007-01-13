package com.googlecode.instinct.internal.util.instance;

import java.io.Serializable;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ObjectFactoryImplAtomicTest extends InstinctTestCase {
    private static final Class<ObjectFactory> AN_INTERFACE_1 = ObjectFactory.class;
    private static final Class<Serializable> AN_INTERFACE_2 = Serializable.class;
    private ObjectFactoryImpl factory;

    public void testProperties() {
        checkClass(ObjectFactoryImpl.class, ObjectFactory.class);
    }

    public void testOnlyAcceptConcreteClasses() {
        checkRejectsInterfaces(AN_INTERFACE_1);
        checkRejectsInterfaces(AN_INTERFACE_2);
    }

    public void testCreatePerformsCorrectTypeInference() {
        checkSucceeds(ObjectFactoryImpl.class);
//        checkSucceeds(BehaviourContextConfigurationException.class);
        checkFails(ObjectCreationException.class);
    }

    private <T> void checkSucceeds(final Class<T> toCreate, final Object... values) {
        factory.create(toCreate, values);
    }

    private <T> void checkFails(final Class<T> toCreate, final Object... values) {
        assertThrows(ObjectCreationException.class, new Runnable() {
            public void run() {
                factory.create(toCreate, values);
            }
        });
    }

    private <T> void checkRejectsInterfaces(final Class<T> anInterface) {
        assertThrows(IllegalArgumentException.class, new Runnable() {
            public void run() {
                new ObjectFactoryImpl().create(anInterface);
            }
        });
    }

    @Override
    public void setUpSubject() {
        factory = new ObjectFactoryImpl();
    }
}
