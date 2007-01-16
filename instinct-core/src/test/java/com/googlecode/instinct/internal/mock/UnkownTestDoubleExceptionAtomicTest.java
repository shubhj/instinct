package com.googlecode.instinct.internal.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionChecker.checkException;

public final class UnkownTestDoubleExceptionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkException(UnkownTestDoubleException.class);
    }
}
