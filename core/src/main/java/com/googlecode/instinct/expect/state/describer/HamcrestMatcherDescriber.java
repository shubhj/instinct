/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matcher;

@Suggest("Remove the dependency on the Hamcrest StringDescription class.")
public final class HamcrestMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherUtil<T> matcherUtil = new CommonMatcherUtilImpl<T>();
    private final T actual;
    private final Matcher<T> matcher;

    public HamcrestMatcherDescriber(final T actual, final Matcher<T> matcher) {
        this.actual = actual;
        this.matcher = matcher;
    }

    public String describe() {
        final StringBuilder builder = new StringBuilder();
        builder.append(newLine()).
                    append("Expected: ").append(getExpectation()).
                append(newLine()).
                    append(createFiveSpaces()).append("got: ").append(getValue()).
                append(newLine());
        return builder.toString();
    }

    private String createFiveSpaces() {
        return matcherUtil.space(5);
    }

    private String newLine() {
        return matcherUtil.newLine();
    }

    private String getValue() {
        return matcherUtil.describeValue(actual);
    }

    private String getExpectation() {
        return matcherUtil.describeExpectation(matcher);
    }
}
