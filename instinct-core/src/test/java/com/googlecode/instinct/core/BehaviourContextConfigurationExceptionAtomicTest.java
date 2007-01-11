package com.googlecode.instinct.core;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class BehaviourContextConfigurationExceptionAtomicTest extends InstinctTestCase {
    private static final String MESSAGE = "message";
    private static final Throwable CAUSE = new Throwable();

    public void testProperties() {
        checkException(BehaviourContextConfigurationException.class);
    }

    public void testMessageConstructor() {
        assertEquals(MESSAGE, new BehaviourContextConfigurationException(MESSAGE).getMessage());
    }

    public void testMessageCauseConstructor() {
        final Throwable t = new BehaviourContextConfigurationException(MESSAGE, CAUSE);
        assertEquals(MESSAGE, t.getMessage());
        assertSame(CAUSE, t.getCause());
    }
}
