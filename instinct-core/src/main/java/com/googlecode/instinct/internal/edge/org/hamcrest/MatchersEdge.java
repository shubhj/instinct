package com.googlecode.instinct.internal.edge.org.hamcrest;

public interface MatchersEdge {
    <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> param1);

    <T> org.hamcrest.Matcher<T> is(T param1);

    <T> org.hamcrest.Matcher<T> is(Class<T> param1);

    <T> org.hamcrest.Matcher<T> not(org.hamcrest.Matcher<T> param1);

    <T> org.hamcrest.Matcher<T> not(T param1);

    <T> org.hamcrest.Matcher<T> equalTo(T param1);

    <T> org.hamcrest.Matcher<T> instanceOf(Class<T> param1);

    <T> org.hamcrest.Matcher<T> allOf(org.hamcrest.Matcher<T>... param1);

    <T> org.hamcrest.Matcher<T> allOf(Iterable<org.hamcrest.Matcher<T>> param1);

    <T> org.hamcrest.Matcher<T> anyOf(org.hamcrest.Matcher<T>... param1);

    <T> org.hamcrest.Matcher<T> anyOf(Iterable<org.hamcrest.Matcher<T>> param1);

    <T> org.hamcrest.Matcher<T> sameInstance(T param1);

    <T> org.hamcrest.Matcher<T> nullValue();

    <T> org.hamcrest.Matcher<T> notNullValue();

    <T> org.hamcrest.Matcher<T> describedAs(String param1, org.hamcrest.Matcher<T> param2, Object... param3);

    <T> org.hamcrest.Matcher<T[]> hasItemInArray(org.hamcrest.Matcher<T> param1);

    <T> org.hamcrest.Matcher<T[]> hasItemInArray(T param1);

    <T> org.hamcrest.Matcher<Iterable<T>> hasItem(T param1);

    <T> org.hamcrest.Matcher<Iterable<T>> hasItem(org.hamcrest.Matcher<T> param1);

    <T> org.hamcrest.Matcher<Iterable<T>> hasItems(org.hamcrest.Matcher<T>... param1);

    <T> org.hamcrest.Matcher<Iterable<T>> hasItems(T... param1);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(org.hamcrest.Matcher<K> param1, org.hamcrest.Matcher<V> param2);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasEntry(K param1, V param2);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(org.hamcrest.Matcher<K> param1);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasKey(K param1);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(org.hamcrest.Matcher<V> param1);

    <K, V> org.hamcrest.Matcher<java.util.Map<K, V>> hasValue(V param1);

    org.hamcrest.Matcher<Double> closeTo(double param1, double param2);

    <T extends Comparable<T>> org.hamcrest.Matcher<T> greaterThan(T param1);

    <T extends Comparable<T>> org.hamcrest.Matcher<T> greaterThanOrEqualTo(T param1);

    <T extends Comparable<T>> org.hamcrest.Matcher<T> lessThan(T param1);

    <T extends Comparable<T>> org.hamcrest.Matcher<T> lessThanOrEqualTo(T param1);

    org.hamcrest.Matcher<String> equalToIgnoringCase(String param1);

    org.hamcrest.Matcher<String> equalToIgnoringWhiteSpace(String param1);

    org.hamcrest.Matcher<String> containsString(String param1);

    org.hamcrest.Matcher<String> endsWith(String param1);

    org.hamcrest.Matcher<String> startsWith(String param1);

    <T> org.hamcrest.Matcher<T> hasToString(org.hamcrest.Matcher<String> param1);

    <T> org.hamcrest.Matcher<Class<?>> typeCompatibleWith(Class<T> param1);

    org.hamcrest.Matcher<java.util.EventObject> eventFrom(Class<? extends java.util.EventObject> param1, Object param2);

    org.hamcrest.Matcher<java.util.EventObject> eventFrom(Object param1);

    <T> org.hamcrest.Matcher<T> hasProperty(String param1);

    <T> org.hamcrest.Matcher<T> hasProperty(String param1, org.hamcrest.Matcher param2);

    org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(String param1, org.hamcrest.Matcher<String> param2);

    org.hamcrest.Matcher<org.w3c.dom.Node> hasXPath(String param1);

    <T> org.hamcrest.Matcher<T> anything();

    <T> org.hamcrest.Matcher<T> anything(String param1);
}
