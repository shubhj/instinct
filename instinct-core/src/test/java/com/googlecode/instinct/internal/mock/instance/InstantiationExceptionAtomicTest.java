package com.googlecode.instinct.internal.mock.instance;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class InstantiationExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkException(InstantiationException.class);
    }
}
