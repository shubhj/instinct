package com.googlecode.instinct.internal.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Verifiable;

public interface MockControl extends Verifiable {
    Object getMockedObject();
    NameMatchBuilder expects(InvocationMatcher expectation);
}
