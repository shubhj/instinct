package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class DummyNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(DummyNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals("^dummy", new DummyNamingConvention().getPattern());
    }
}
