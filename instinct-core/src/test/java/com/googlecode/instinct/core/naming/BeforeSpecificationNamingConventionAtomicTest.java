package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;

public final class BeforeSpecificationNamingConventionAtomicTest extends InstinctTestCase {
    public void testGetPattern() {
        assertEquals("^setUp.*",  new BeforeSpecificationNamingConvention().getPattern());
    }
}
