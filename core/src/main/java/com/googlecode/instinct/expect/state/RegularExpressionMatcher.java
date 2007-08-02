package com.googlecode.instinct.expect.state;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class RegularExpressionMatcher extends TypeSafeMatcher<String> {
    private final String regularExpression;

    @Suggest("Come back here after creating the PatternEdge")
    public RegularExpressionMatcher(final String regularExpression) {
        checkNotNull(regularExpression);
        this.regularExpression = regularExpression;
    }

    @Override
    public boolean matchesSafely(final String item) {
        checkNotNull(item);
        return false;
    }

    public void describeTo(final Description description) {
        checkNotNull(description);
        description.appendText("a string matching regular expression /").appendText(regularExpression).appendText("/");
    }
}
