package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Make this use JMock rather than EasyMock", "This probably belongs in the production tree"})
public final class MockCreator {
//    private static final MockFactoryImpl MOCK_FACTORY = new MockFactoryImpl();

    private MockCreator() {
        throw new UnsupportedOperationException();
    }
/*
    public static <T> T createMock(final Class<T> toMock) {
        throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createMock(toMock);
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
