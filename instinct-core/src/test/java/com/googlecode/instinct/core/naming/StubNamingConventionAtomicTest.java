package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class StubNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(StubNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals("^stub", new StubNamingConvention().getPattern());
    }
}
