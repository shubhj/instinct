package com.googlecode.instinct.internal.testdouble;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class TestDoubleAutoWirerImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(TestDoubleAutoWirerImpl.class, TestDoubleAutoWirer.class);
    }
}
