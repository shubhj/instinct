package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class BehaviourContextNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(BehaviourContextNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals(".*BehaviourContext$", new BehaviourContextNamingConvention().getPattern());
    }
}
