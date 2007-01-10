package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;

public final class AfterSpecificationNamingConventionAtomicTest extends InstinctTestCase {
    public void testGetPattern() {
        assertEquals("^tearDown.*", new AfterSpecificationNamingConvention().getPattern());
    }
}
