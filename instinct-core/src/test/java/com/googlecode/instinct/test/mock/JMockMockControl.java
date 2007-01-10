package com.googlecode.instinct.test.mock;

import org.jmock.Mock;
import org.jmock.core.InvocationMatcher;
import org.jmock.builder.NameMatchBuilder;

public final class JMockMockControl implements MockControl {
    private final Mock mockController;

    public JMockMockControl(final Mock mockController) {
        this.mockController = mockController;
    }

    public Object getMockedObject() {
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
