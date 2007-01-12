package com.googlecode.instinct.internal.util.instance;

import java.io.Serializable;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ObjectFactoryAtomicTest extends InstinctTestCase {
    private static final Class<ObjectFactory> AN_INTERFACE_1 = ObjectFactory.class;
    private static final Class<Serializable> AN_INTERFACE_2 = Serializable.class;

    public void testProperties() {
        checkClass(ObjectFactoryImpl.class, ObjectFactory.class);
    }

    public void testOnlyAcceptConcreteClasses() {
        checkRejectsInterfaces(AN_INTERFACE_1);
        checkRejectsInterfaces(AN_INTERFACE_2);
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
    }
}
