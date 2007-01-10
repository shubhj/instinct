package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;

public final class BehaviourContextNamingConventionAtomicTest extends InstinctTestCase {
    public void testGetPattern() {
        assertEquals(".*BehaviourContext$",  new BehaviourContextNamingConvention().getPattern());
    }
}
