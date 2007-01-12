package com.googlecode.instinct.core;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ExceptionChecker;

public final class LifeCycleMethodConfigurationExceptionAtomicTest extends InstinctTestCase {
    private static final String MESSAGE = "message";
    private static final Throwable CAUSE = new Throwable();

    public void testProperties() {
        ExceptionChecker.checkException(LifeCycleMethodConfigurationException.class);
    }

    public void testMessageConstructor() {
        assertEquals(MESSAGE, new LifeCycleMethodConfigurationException(MESSAGE).getMessage());
    }

    public void testMessageCauseConstructor() {
        final Throwable t = new LifeCycleMethodConfigurationException(MESSAGE, CAUSE);
        assertEquals(MESSAGE, t.getMessage());
        assertSame(CAUSE, t.getCause());
    }
}
