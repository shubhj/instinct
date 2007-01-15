package com.googlecode.instinct.core.naming;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AfterSpecificationNamingConventionAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(AfterSpecificationNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        assertEquals("^tearDown", new AfterSpecificationNamingConvention().getPattern());
    }
}
