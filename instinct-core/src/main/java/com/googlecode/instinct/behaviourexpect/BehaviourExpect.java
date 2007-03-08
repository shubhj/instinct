package com.googlecode.instinct.behaviourexpect;

import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Stub;

// DEBT ConstantName {
public final class BehaviourExpect {
    public static final BehaviourExpectations expect = new BehaviourExpectationsImpl();

    private BehaviourExpect() {
        throw new UnsupportedOperationException();
    }

    public static <T> T one(final T mockedObject) {
        return expect.one(mockedObject);
    }

    public static <T> NameMatchBuilder call(final T mockedObject) {
        return expect.call(mockedObject);
    }

    public static MethodInvocations that() {
        return expect.that();
    }

    public static void that(final Expectations expectations) {
        expect.that(expectations);
    }

    public static <T> MethodInvocations that(final T mockedObject) {
        return expect.that(mockedObject);
    }

    public static IdentityBuilder will(final Stub stubAction) {
        return expect.will(stubAction);
    }

    public static Stub returnValue(final Object returnValue) {
        return expect.returnValue(returnValue);
    }
}
// } DEBT ConstantName
