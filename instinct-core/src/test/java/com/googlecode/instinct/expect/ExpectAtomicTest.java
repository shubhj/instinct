package com.googlecode.instinct.expect;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ClassChecker;

public class ExpectAtomicTest extends InstinctTestCase {

    public void testClassProperties() {
        ClassChecker.checkClass(Expect.class, Object.class);
    }

    public void testThatExpectStaticFieldIsOfCorrectType() {
        assertSame(ExpectThatImpl.class, Expect.expect.getClass());
    }
}

