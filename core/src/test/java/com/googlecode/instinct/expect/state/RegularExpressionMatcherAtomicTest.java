package com.googlecode.instinct.expect.state;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;

public final class RegularExpressionMatcherAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(RegularExpressionMatcher.class, TypeSafeMatcher.class);
    }

    public void testDescribesErrorsByAppendingTheExpectedRegularExpression() {
        checkDescribesErrorsByAppendingTheExpectedRegularExpression(".*");
        checkDescribesErrorsByAppendingTheExpectedRegularExpression("[a-z]+");
    }

    private void checkDescribesErrorsByAppendingTheExpectedRegularExpression(final String regularExpression) {
        final Matcher<String> matcher = new RegularExpressionMatcher(regularExpression);
        final Description description = new StringDescription();
        matcher.describeTo(description);
        expect.that(description.toString()).containsString("a string matching regular expression /" + regularExpression + '/');
    }

}
