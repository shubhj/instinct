/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

import org.hamcrest.Matcher;

public final class MatcherVerifierImpl implements MatcherVerifier {

    public <T> void assertThat(final T actual, final Matcher<T> matcher, final MatcherDescriber describing) {
        if (!matcher.matches(actual)) {
            throw new AssertionError(describing.toString());
        }
    }
}

