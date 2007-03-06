package com.googlecode.instinct.behaviourexpect;

import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Stub;

interface BehaviourExpectations {
    <T> T one(final T mockedObject);

    <T> NameMatchBuilder call(final T mockedObject);

    MethodInvocations that();

    void that(final Expectations expectations);

    <T> MethodInvocations that(final T mockedObject);

    IdentityBuilder will(final Stub stubAction);

    Stub returnValue(Object returnValue);
}
