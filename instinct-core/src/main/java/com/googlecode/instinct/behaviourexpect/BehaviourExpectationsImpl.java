package com.googlecode.instinct.behaviourexpect;

import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.builder.StubBuilder;
import org.jmock.core.Stub;

public final class BehaviourExpectationsImpl implements BehaviourExpectations {

    public <T> T one(final T mockedObject) {
        return null;
    }

    public <T> NameMatchBuilder call(final T mockedObject) {
        return null;
    }

    public MethodInvocations that() {
        return null;
    }

    public void that(final Expectations expectations) {

    }

    public <T> StubBuilder that(final T mockedObject) {
        return null;
    }

    public IdentityBuilder will(final Stub stubAction) {
        return null;
    }

    public Stub returnValue(final Object returnValue) {
        return null;
    }
}
