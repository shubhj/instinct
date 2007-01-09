package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Mock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;
import org.jmock.cglib.CGLIBCoreMock;

@SuppressWarnings({"unchecked"})
@Suggest({"Move this into the production tree -> pull out an interface & make non-static"})
public final class Mocker {

    private Mocker() {
        throw new UnsupportedOperationException();
    }

    @Suggest("Will need to keep a track of the mock by adding it to a map so we can get to the control & the proxy")
    public static <T> T mock(final Class<T> toMock) {
        return toMock.isInterface() ? newInterfaceMock(toMock) : newConcreteMock(toMock);
    }

    private static <T> T newInterfaceMock(final Class<T> toMock) {
        return (T) new Mock(toMock).proxy();
    }

    private static <T> T newConcreteMock(final Class<T> toMock) {
        return (T) new CGLIBCoreMock(toMock, mockNameFromClass(toMock)).proxy();
    }

/*
public static <T> T mock(final Class<T> toMock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.mock(toMock);
}

public static <T> T createNiceMock(final Class<T> toMock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createNiceMock(toMock);
}

public static IMocksControl getControl(final Object mock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.getControl(mock);
}

public static IMocksControl createControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createControl();
}

public static IMocksControl createNiceControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createNiceControl();
}

public static IMocksControl createStrictControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createStrictControl();
}

public static void replay() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.replay();
}

public static void verify() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.verify();
}

public static void reset() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.reset();
}*/
}
