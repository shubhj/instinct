package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
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

    public void testEqualsIgnoringCase() {
        expectNullRejected("equalsIgnoringCase", new Runnable() {
            public void run() {
                checker.equalsIgnoringCase(null);
            }
        });
    }

    /*
    public final void equalsIgnoringCase(final String string) {
    public final void equalsIgnoringWhiteSpace(final String string) {
    public final void notEqualIgnoringCase(final String string) {
    public final void notEqualIgnoringWhiteSpace(final String string) {
    public final void notContainString(final String string) {
    public final void notEndingWith(final String string) {
    public final void startsWith(final String string) {
    public final void notStartingWith(final String string) {
     */

    public void testEndsWithShowsHumanReadableStringWhenPassedNull() {
        expectNullRejected("endsWith", new Runnable() {
            public void run() {
                checker.endsWith(null);
            }
        });
    }

    public void testContainsStringShowsHumanReadableStringWhenPassedNull() {
        expectNullRejected("containsString", new Runnable() {
            public void run() {
                checker.containsString(null);
            }
        });
    }

    private void expectNullRejected(final String methodName, final Runnable block) {
        assertThrows(IllegalArgumentException.class, "Cannot pass a null string into " + methodName, block);
    }
}
