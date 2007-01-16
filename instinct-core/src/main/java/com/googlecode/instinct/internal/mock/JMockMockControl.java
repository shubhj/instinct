package com.googlecode.instinct.internal.mock;

import org.jmock.Mock;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.InvocationMatcher;

public final class JMockMockControl implements MockControl {
    private final Mock mockController;

    public JMockMockControl(final Mock mockController) {
        this.mockController = mockController;
    }

    public Object createTestDouble() {
        return mockController.proxy();
    }

    public NameMatchBuilder expects(final InvocationMatcher expectation) {
        return mockController.expects(expectation);
    }

    public void verify() {
        mockController.verify();
    }

    @Override
    public String toString() {
        return mockController.toString();
    }
}
