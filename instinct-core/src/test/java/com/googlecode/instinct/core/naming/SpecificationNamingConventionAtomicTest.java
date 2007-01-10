package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;

public final class SpecificationNamingConventionAtomicTest extends InstinctTestCase {
    public void testGetPattern() {
        assertEquals("^must.*",  new SpecificationNamingConvention().getPattern());
    }
}
