package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;

public final class StringCheckerImplAtomicTest extends InstinctTestCase {
    private static final String SUBJECT_STRING = getInstance(String.class);
    private StringChecker checker;

    @Override
    public void setUpSubject() {
        checker = new StringCheckerImpl(SUBJECT_STRING);
    }

    public void testConformsToClassTraits() {
        checkPublic(StringCheckerImpl.class);
    }

    public void testEndsWithShowsHumanReadableStringWhenNullPassed() {
        try {
            checker.endsWith(null);
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            final String message = e.getMessage();
            assertEquals("Cannot pass a null string into endsWith", message);
        } catch (Throwable t) {
            fail("Expected IllegalArgumentException but was " + t);
        }
    }

    public void testContainsStringShowsHumanReadableStringWhenNullPassed() {
        try {
            checker.containsString(null);
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            final String message = e.getMessage();
            assertEquals("Cannot pass a null string into containsString", message);
        } catch (Throwable t) {
            fail("Expected IllegalArgumentException but was " + t);
        }
    }
}
