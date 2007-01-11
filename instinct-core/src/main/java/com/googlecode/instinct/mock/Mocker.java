package com.googlecode.instinct.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;

public final class Mocker {
    private static final Mockery MOCKERY = new MockeryImpl();

    private Mocker() {
        throw new UnsupportedOperationException();
    }

    public static <T> T mock(final Class<T> toMock) {
        return MOCKERY.mock(toMock);
    }

    public static NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        return MOCKERY.expects(mockedObject, expectation);
    }

    public static InvocationMatcher once() {
        return MOCKERY.once();
    }

    public static Constraint same(final Object argument) {
        return MOCKERY.same(argument);
    }

    public static Constraint anything() {
        return MOCKERY.anything();
    }

    public static Constraint eq(final Object argument) {
        return MOCKERY.eq(argument);
    }

    public static Stub returnValue(final Object returnValue) {
        return MOCKERY.returnValue(returnValue);
    }

    public static void verify() {
        MOCKERY.verify();
    }
}
