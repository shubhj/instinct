/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.expect.state.matcher;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.util.regex.Pattern;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Performs matches of a string against a regular expression.
 */
public final class RegularExpressionMatcher extends TypeSafeMatcher<String> {
    private final Pattern pattern;

    private RegularExpressionMatcher(final String regularExpression) {
        pattern = Pattern.compile(regularExpression);
    }

    private RegularExpressionMatcher(final String regularExpression, final int flags) {
        pattern = Pattern.compile(regularExpression, flags);
    }

    /**
     * Returns true if the <code>item</code> matches the regular expression given in the constructor.
     * This method is invoked by Hamcrest after preliminary null and type checking.
     * @param item The item to check against the regular expression given in the constructor.
     * @return <code>true</code> if the <code>item</code> matches the regular expression given in the constructor.
     */
    @Override
    public boolean matchesSafely(final String item) {
        checkNotNull(item);
        return pattern.matcher(item).matches();
    }

    public void describeTo(final Description description) {
        checkNotNull(description);
        description.appendText("a string matching regular expression /").appendText(pattern.toString()).appendText("/");
    }

    /**
     * Returns a matcher for the given <code>regularExpression</code>.
     * @param regularExpression The regular expression to match against.
     * @throws java.util.regex.PatternSyntaxException if the given <code>regularExpression</code> is syntactically invalid.
     */
    @Factory
    public static Matcher<String> matchesRegex(final String regularExpression) {
        checkNotNull(regularExpression);
        return new RegularExpressionMatcher(regularExpression);
    }

    /**
     * Returns a matcher for the given <code>regularExpression</code>.
     * @param regularExpression The regular expression to match against.
     * @param flags Match flags, a bit mask passed directly to  {@link Pattern#compile(String, int)}.
     * @throws java.util.regex.PatternSyntaxException if the given <code>regularExpression</code> is syntactically invalid.
     */
    @Factory
    public static Matcher<String> matchesRegex(final String regularExpression, final int flags) {
        checkNotNull(regularExpression);
        return new RegularExpressionMatcher(regularExpression, flags);
    }
}
