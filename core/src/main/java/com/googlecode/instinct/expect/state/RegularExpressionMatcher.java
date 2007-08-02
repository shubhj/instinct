package com.googlecode.instinct.expect.state;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class RegularExpressionMatcher extends TypeSafeMatcher<String> {

    @Override
    public boolean matchesSafely(final String item) {
        checkNotNull(item);
        return false;
    }

    public void describeTo(final Description description) {
        checkNotNull(description);
    }
}
