package com.googlecode.instinct.internal.edge.org.hamcrest;

import org.hamcrest.Matcher;

public interface MatcherAssertEdge {
    <T> void expectThat(T t, Matcher<T> matcher);

    <T> void expectNotThat(T t, Matcher<T> matcher);
}
