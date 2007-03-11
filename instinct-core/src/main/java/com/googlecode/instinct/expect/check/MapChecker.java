package com.googlecode.instinct.expect.check;

import java.util.Map;

public interface MapChecker<K,V> extends ObjectChecker<Map<K,V>> {
    void containsEntry(org.hamcrest.Matcher<K> matcher, org.hamcrest.Matcher<V> matcher1);

    void containsEntry(K k, V v);

    void notContainEntry(org.hamcrest.Matcher<K> matcher, org.hamcrest.Matcher<V> matcher1);

    void notContainEntry(K k, V v);

    void containsKey(org.hamcrest.Matcher<K> matcher);

    void containsKey(K k);

    void notContainKey(org.hamcrest.Matcher<K> matcher);

    void notContainKey(K k);

    void containsValue(org.hamcrest.Matcher<V> matcher);

    void containsValue(V v);

    void notContainValue(org.hamcrest.Matcher<V> matcher);

    void notContainValue(V v);

    void isEmpty();

    void notEmpty();

    void hasSize(int size);
}
