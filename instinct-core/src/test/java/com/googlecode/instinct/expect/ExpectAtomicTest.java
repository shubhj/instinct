package com.googlecode.instinct.expect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ExpectAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(Expect.class, Object.class);
    }

    public void testThatExpectStaticFieldIsOfCorrectType() {
        assertSame(ExpectThatImpl.class, expect.getClass());
    }
}

