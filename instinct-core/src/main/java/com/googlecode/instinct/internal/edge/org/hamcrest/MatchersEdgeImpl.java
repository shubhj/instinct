package com.googlecode.instinct.internal.edge.org.hamcrest;

import org.hamcrest.Matchers;

public class MatchersEdgeImpl implements MatchersEdge {

    public final <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> param1) {
        return Matchers.is(param1);
    }

    public final <T> org.hamcrest.Matcher<T> is(T param1) {
        return Matchers.is(param1);
    }

    public final <T> org.hamcrest.Matcher<T> is(java.lang.Class<T> param1) {
        return Matchers.is(param1);
    }

    public final <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> param1) {
        return Matchers.not(param1);
    }

    public final <T> org.hamcrest.Matcher<T> not(T param1) {
        return Matchers.not(param1);
    }

    public final <T> org.hamcrest.Matcher<T> equalTo(T param1) {
        return Matchers.equalTo(param1);
    }

    public final <T> org.hamcrest.Matcher<T> instanceOf(java.lang.Class<T> param1) {
        return Matchers.instanceOf(param1);
    }

    public final <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<T>... param1) {
        return Matchers.allOf(param1);
    }

    public final <T> org.hamcrest.Matcher<T> allOf(java.lang.Iterable<org.hamcrest.Matcher<T>> param1) {
        return Matchers.allOf(param1);
    }

    public final <T> org.hamcrest.Matcher<T> anyOf(org.hamcrest.Matcher<T>... param1) {
        return Matchers.anyOf(param1);
    }

    public final <T> org.hamcrest.Matcher<T> anyOf(java.lang.Iterable<org.hamcrest.Matcher<T>> param1) {
        return Matchers.anyOf(param1);
    }

    public final <T> org.hamcrest.Matcher<T> sameInstance(T param1) {
        return Matchers.sameInstance(param1);
    }

    public final <T> org.hamcrest.Matcher<T> anything() {
        return Matchers.anything();
    }

    public final <T> org.hamcrest.Matcher<T> anything(java.lang.String param1) {
        return Matchers.anything(param1);
    }

    public final <T> org.hamcrest.Matcher<T> nullValue() {
        return Matchers.nullValue();
    }

    public final <T> org.hamcrest.Matcher<T> notNullValue() {
        return Matchers.notNullValue();
    }

    public final <T> org.hamcrest.Matcher<T> describedAs(java.lang.String param1, org.hamcrest.Matcher<T> param2,
                                                         java.lang.Object... param3) {
        return Matchers.describedAs(param1, param2, param3);
    }

    public final <T> org.hamcrest.Matcher<T[]> hasItemInArray(org.hamcrest.Matcher<T> param1) {
        return Matchers.hasItemInArray(param1);
    }

    public final <T> org.hamcrest.Matcher<T[]> hasItemInArray(T param1) {
        return Matchers.hasItemInArray(param1);
    }

    public final <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(T param1) {
        return Matchers.hasItem(param1);
    }

    public final <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(org.hamcrest.Matcher<T> param1) {
        return Matchers.hasItem(param1);
    }

    public final <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<T>... param1) {
        return Matchers.hasItems(param1);
    }

    public final <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(T... param1) {
        return Matchers.hasItems(param1);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(org.hamcrest.Matcher<K> param1, org.hamcrest.Matcher<V> param2) {
        return Matchers.hasEntry(param1, param2);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(K param1, V param2) {
        return Matchers.hasEntry(param1, param2);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(org.hamcrest.Matcher<K> param1) {
        return Matchers.hasKey(param1);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(K param1) {
        return Matchers.hasKey(param1);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(org.hamcrest.Matcher<V> param1) {
        return Matchers.hasValue(param1);
    }

    public final <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(V param1) {
        return Matchers.hasValue(param1);
    }

    public final org.hamcrest.Matcher<java.lang.Double> closeTo(double param1, double param2) {
        return Matchers.closeTo(param1, param2);
    }

    public final <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T param1) {
        return Matchers.greaterThan(param1);
    }

    public final <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T param1) {
        return Matchers.greaterThanOrEqualTo(param1);
    }

    public final <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThan(T param1) {
        return Matchers.lessThan(param1);
    }

    public final <T extends java.lang.Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T param1) {
        return Matchers.lessThanOrEqualTo(param1);
    }

    public final org.hamcrest.Matcher<java.lang.String> equalToIgnoringCase(java.lang.String param1) {
        return Matchers.equalToIgnoringCase(param1);
    }

    public final org.hamcrest.Matcher<java.lang.String> equalToIgnoringWhiteSpace(java.lang.String param1) {
        return Matchers.equalToIgnoringWhiteSpace(param1);
    }

    public final org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String param1) {
        return Matchers.containsString(param1);
    }

    public final org.hamcrest.Matcher<java.lang.String> endsWith(java.lang.String param1) {
        return Matchers.endsWith(param1);
    }

    public final org.hamcrest.Matcher<java.lang.String> startsWith(java.lang.String param1) {
        return Matchers.startsWith(param1);
    }

    public final <T> org.hamcrest.Matcher<T> hasToString(org.hamcrest.Matcher<java.lang.String> param1) {
        return Matchers.hasToString(param1);
    }

    public final <T> org.hamcrest.Matcher<java.lang.Class<?>> typeCompatibleWith(java.lang.Class<T> param1) {
        return Matchers.typeCompatibleWith(param1);
    }

    public final org.hamcrest.Matcher<java.util.EventObject> eventFrom(
            java.lang.Class<? extends java.util.EventObject> param1, java.lang.Object param2) {
        return Matchers.eventFrom(param1, param2);
    }

    public final org.hamcrest.Matcher<java.util.EventObject> eventFrom(java.lang.Object param1) {
        return Matchers.eventFrom(param1);
    }

    public final <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String param1) {
        return Matchers.hasProperty(param1);
    }

    public final <T> org.hamcrest.Matcher<T> hasProperty(java.lang.String param1, org.hamcrest.Matcher param2) {
        return Matchers.hasProperty(param1, param2);
    }

    public final org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String param1,
                                                                 org.hamcrest.Matcher<java.lang.String> param2) {
        return Matchers.hasXPath(param1, param2);
    }

    public final org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(java.lang.String param1) {
        return Matchers.hasXPath(param1);
    }
}
