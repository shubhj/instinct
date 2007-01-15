package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class MockNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(MockNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals("^mock", new MockNamingConvention().getPattern());
    }
}
