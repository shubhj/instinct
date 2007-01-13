package com.googlecode.instinct.core;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ExceptionChecker;

public final class LifeCycleMethodConfigurationExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        ExceptionChecker.checkException(LifeCycleMethodConfigurationException.class);
    }
}
