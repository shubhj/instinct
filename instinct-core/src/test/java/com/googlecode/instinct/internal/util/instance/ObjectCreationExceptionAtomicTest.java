package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class ObjectCreationExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkException(ObjectCreationException.class);
    }
}
