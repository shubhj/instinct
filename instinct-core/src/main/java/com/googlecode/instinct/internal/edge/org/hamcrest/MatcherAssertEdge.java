package com.googlecode.instinct.internal.edge.org.hamcrest;

public interface MatcherAssertEdge {
    <T> void expectThat(T t, org.hamcrest.Matcher<T> matcher);
    <T> void expectNotThat(T t, org.hamcrest.Matcher<T> matcher);
}
