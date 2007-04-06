package com.googlecode.instinct.expect.check;

import java.util.Map;
import org.hamcrest.Matcher;

public interface MapChecker<K, V> extends ObjectChecker<Map<K, V>> {
    void containsEntry(Matcher<K> matcher, Matcher<V> matcher1);

    void containsEntry(K k, V v);

    void notContainEntry(Matcher<K> matcher, Matcher<V> matcher1);

    void notContainEntry(K k, V v);

    void containsKey(Matcher<K> matcher);

    void containsKey(K k);

    void notContainKey(Matcher<K> matcher);

    void notContainKey(K k);

    void containsValue(Matcher<V> matcher);

    void containsValue(V v);

    void notContainValue(Matcher<V> matcher);

    void notContainValue(V v);

    void isEmpty();

    void notEmpty();

    void hasSize(int size);
}
