/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

import org.hamcrest.Matcher;

public interface MatcherVerifier {
    <T> void assertThat(T actual, Matcher<T> matcher, MatcherDescriber describing);
    <T> void assertThat(T actual, Matcher<T> matcher);
}
