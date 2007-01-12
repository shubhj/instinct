package com.googlecode.instinct.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;

public interface Mockery {
    <T> T mock(Class<T> toMock);

    <T> T mock(Class<T> toMock, String roleName);

    NameMatchBuilder expects(Object mockedObject, InvocationMatcher expectation);

    InvocationMatcher once();

    Constraint same(Object argument);

    Constraint anything();

    Constraint eq(Object argument);

    Stub returnValue(Object returnValue);

    void verify();
}
