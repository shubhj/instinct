package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.hamcrest.TypeSafeMatcher;

public final class RegularExpressionMatcherAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(RegularExpressionMatcher.class, TypeSafeMatcher.class);
    }
}
