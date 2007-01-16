package com.googlecode.instinct.core;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class LifeCycleMethodConfigurationExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkException(LifeCycleMethodConfigurationException.class);
    }
}
