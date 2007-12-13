/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberBuilder;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberImpl;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matcher;

@Suggest("Remove the dependency on the Hamcrest StringDescription class.")
public final class HamcrestMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherDescriber<T> matcherDescriber = new CommonMatcherDescriberImpl<T>();
    private final T actual;
    private final Matcher<T> matcher;

    public HamcrestMatcherDescriber(final T actual, final Matcher<T> matcher) {
        this.actual = actual;
        this.matcher = matcher;
    }

    public String describe() {
        final MatcherDescriberBuilder builder = new MatcherDescriberImpl().
                addNewLine().
                setExpectedLabelName("Expected").addColon().addSpace().setExpectedValue(matcherDescriber.describeExpectation(matcher)).
                addNewLine().
                addSpace(5).setReturnedLabelName("got").addColon().addSpace().setReturnedValue(matcherDescriber.describeValue(actual)).
                addNewLine();
        return builder.describe();
    }
}
