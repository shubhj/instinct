/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

import com.googlecode.instinct.expect.state.describer.HamcrestMatcherDescriber;
import org.hamcrest.Matcher;

public final class MatcherVerifierImpl implements MatcherVerifier {

    public <T> void assertThat(final T actual, final Matcher<T> matcher, final MatcherDescriber describing) {
        if (!matcher.matches(actual)) {
            throw new AssertionError(describing.describe());
        }
    }

    public <T> void assertThat(final T actual, final Matcher<T> matcher) {
        assertThat(actual, matcher, new HamcrestMatcherDescriber<T>(actual, matcher));
    }
}

