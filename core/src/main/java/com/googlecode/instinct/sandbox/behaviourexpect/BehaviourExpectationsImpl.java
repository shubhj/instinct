package com.googlecode.instinct.sandbox.behaviourexpect;

import com.googlecode.instinct.internal.mock.JMock12Mockery;
import com.googlecode.instinct.internal.mock.JMock12MockeryImpl;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Expectations;
import org.jmock.builder.ArgumentsMatchBuilder;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.stub.ThrowStub;

@Suggest({"Null checks.",
        "This class should just delegate to the relevent mockery, instinct or jMock."})
public final class BehaviourExpectationsImpl implements BehaviourExpectations {
    private final JMock12Mockery instinctJMock12Mockery = new JMock12MockeryImpl();
    private final org.jmock.Mockery jMock2Mockery = new org.jmock.Mockery();

    public <T> T one(final T mockedObject) {
        return null;
    }

    public ArgumentsMatchBuilder method(final String name) {
        return null;
    }

    public ArgumentsMatchBuilder method(final Constraint nameConstraint) {
        return null;
    }

    public MethodInvocationMatcher that() {
        return null;
    }

    public <T> MethodInvocationMatcher that(final T mockedObject) {
        return null;
    }

    public <T> MethodInvocationMatcher that(final T mockedObject, final InvocationMatcher numberOfTimes) {
        return null;
    }

    public InvocationMatcher once() {
        return instinctJMock12Mockery.once();
    }

    public InvocationMatcher times(final int expectedNumberOfCalls) {
        return null;
    }

    public InvocationMatcher atLeastOnce() {
        return null;
    }

    public InvocationMatcher anyTimes() {
        return null;
    }

    public IdentityBuilder will(final Stub stubAction) {
        return null;
    }

    public Stub returnValue(final Object returnValue) {
        return null;
    }

    public Stub throwException(final Throwable throwable) {
        return new ThrowStub(throwable);
    }

    public Constraint same(final Object argument) {
        return null;
    }

    public Constraint anything() {
        return null;
    }

    public Constraint eq(final Object argument) {
        return null;
    }

    public Constraint sameElements(final Object[] argument) {
        return null;
    }

    public void that(final Expectations expectations) {
        jMock2Mockery.checking(expectations);
    }
}
