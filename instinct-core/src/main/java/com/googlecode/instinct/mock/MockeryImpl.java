package com.googlecode.instinct.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.stub.ReturnStub;

public final class MockeryImpl implements Mockery {
    private final Verifier verifier = new VerifierImpl();
    private final MockHolder holder = new MockHolderImpl();
    private final MockCreator mockCreator = new JMockMockCreator();

    @SuppressWarnings({"unchecked"})
    public <T> T mock(final Class<T> toMock) {
        final MockControl mockControl = mockCreator.createMockController(toMock);
        final Object mockedObject = mockControl.getMockedObject();
        holder.addControl(mockControl, mockedObject);
        verifier.addVerifiable(mockControl);
        return (T) mockedObject;
    }

    public NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        final MockControl mockController = holder.getMockController(mockedObject);
        return mockController.expects(expectation);
    }

    public InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }

    public Constraint same(final Object argument) {
        return new IsSame(argument);
    }

    public Constraint anything() {
        return new IsAnything();
    }

    public Constraint eq(final Object argument) {
        return new IsEqual(argument);
    }

    public Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    public void verify() {
        verifier.verify();
    }
}
