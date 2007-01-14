package com.googlecode.instinct.internal.util.instance;

import java.io.Serializable;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

@SuppressWarnings({"ExceptionClassNameDoesntEndWithException"})
public final class ObjectFactoryImplAtomicTest extends InstinctTestCase {
    private static final Class<ObjectFactory> AN_INTERFACE_1 = ObjectFactory.class;
    private static final Class<Serializable> AN_INTERFACE_2 = Serializable.class;
    private ObjectFactory factory;
    private Throwable throwable;
    private String message;

    public void testProperties() {
        checkClass(ObjectFactoryImpl.class, ObjectFactory.class);
    }

    public void testOnlyAcceptConcreteClasses() {
        checkRejectsInterfaces(AN_INTERFACE_1);
        checkRejectsInterfaces(AN_INTERFACE_2);
    }

    @Suggest({"Check ordering", "Check super classes/subclasses"})
    public void testCreatePerformsCorrectTypeInference() {
        checkSimpleArgumentsSucceed();
        checkIncorrectOrderingFails();
        checkBadConstructorParametersFails();
        checkInstatiationOfInterfaceFails();
    }

    private void checkSimpleArgumentsSucceed() {
        checkSucceeds(ObjectFactoryImpl.class);
        checkSucceeds(BehaviourContextConfigurationException.class, message);
        checkSucceeds(BehaviourContextConfigurationException.class, message, throwable);
    }

    private void checkIncorrectOrderingFails() {
        checkFails(BehaviourContextConfigurationException.class, throwable, message);
    }

    private void checkBadConstructorParametersFails() {
        checkFails(ObjectCreationException.class);
        checkFails(ObjectCreationException.class);
    }

    private void checkInstatiationOfInterfaceFails() {
        checkFailsWithException(IllegalArgumentException.class, ObjectFactory.class);
        checkFailsWithException(IllegalArgumentException.class, Serializable.class);
    }

    private <T> void checkSucceeds(final Class<T> toCreate, final Object... values) {
        factory.create(toCreate, values);
    }

    private <T> void checkFails(final Class<T> toCreate, final Object... values) {
        checkFailsWithException(ObjectCreationException.class, toCreate, values);
    }

    private <T, E extends RuntimeException> void checkFailsWithException(final Class<E> expectedException, final Class<T> toCreate,
            final Object... values) {
        assertThrows(expectedException, new Runnable() {
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
    public void setUpTestDoubles() {
        message = "Message";
        throwable = mock(Throwable.class);
    }

    @Override
    public void setUpSubject() {
        factory = new ObjectFactoryImpl();
    }
}
