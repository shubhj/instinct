package com.googlecode.instinct.behaviourexpect.behaviour.jmock1;

import com.googlecode.instinct.behaviourexpect.MethodCardinalityInvocations;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;

public interface JMock1Expectations {
    <T> MethodCardinalityInvocations that(T mockedObject);

    IdentityBuilder will(final Stub stubAction);

    Stub returnValue(Object returnValue);

    Stub throwException(Throwable throwable);

    InvocationMatcher once();

    InvocationMatcher times(int expectedNumberOfCalls);

//    InvocationMatcher times(int minNumberOfCalls, int maxNumberOfCalls);

    InvocationMatcher atLeastOnce();

    InvocationMatcher anyTimes();

    Constraint same(Object argument);

    Constraint anything();

    Constraint eq(Object argument);

    Constraint sameElements(Object[] argument);
}
