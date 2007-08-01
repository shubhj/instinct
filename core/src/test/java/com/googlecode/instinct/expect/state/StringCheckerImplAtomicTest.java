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
        expectNullRejected("endsWith", new Runnable() {
            public void run() {
                checker.endsWith(null);
            }
        });
    }

    public void testContainsStringShowsHumanReadableStringWhenNullPassed() {
        expectNullRejected("containsString", new Runnable() {
            public void run() {
                checker.containsString(null);
            }
        });
    }

    private void expectNullRejected(final String methodName, final Runnable block) {
        try {
            block.run();
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            final String message = e.getMessage();
            assertEquals("Cannot pass a null string into " + methodName, message);
        } catch (Throwable t) {
            fail("Expected IllegalArgumentException but was " + t);
        }
    }
}
