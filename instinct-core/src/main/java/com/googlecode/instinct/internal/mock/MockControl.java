package com.googlecode.instinct.internal.mock;

import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.InvocationMatcher;

public interface MockControl extends TestDoubleControl {
    NameMatchBuilder expects(InvocationMatcher expectation);
}
