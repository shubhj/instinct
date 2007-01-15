package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class BeforeSpecificationNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(BeforeSpecificationNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals("^setUp", new BeforeSpecificationNamingConvention().getPattern());
    }
}
