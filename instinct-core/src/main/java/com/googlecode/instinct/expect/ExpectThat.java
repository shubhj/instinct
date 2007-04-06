package com.googlecode.instinct.expect;

import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import com.googlecode.instinct.expect.check.ArrayChecker;
import com.googlecode.instinct.expect.check.ClassChecker;
import com.googlecode.instinct.expect.check.CollectionChecker;
import com.googlecode.instinct.expect.check.ComparableChecker;
import com.googlecode.instinct.expect.check.DoubleChecker;
import com.googlecode.instinct.expect.check.EventObjectChecker;
import com.googlecode.instinct.expect.check.IterableChecker;
import com.googlecode.instinct.expect.check.MapChecker;
import com.googlecode.instinct.expect.check.NodeChecker;
import com.googlecode.instinct.expect.check.ObjectChecker;
import com.googlecode.instinct.expect.check.StringChecker;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

public interface ExpectThat {
    <T> ObjectChecker<T> that(T object);

    StringChecker that(String string);

    <T extends Comparable<T>> ComparableChecker<T> that(T comparable);

    <E, T extends Iterable<E>> IterableChecker<E, T> that(T iterable);

    <E, T extends Collection<E>> CollectionChecker<E, T> that(T collection);

    <T> ArrayChecker<T> that(T[] array);

    <K, V> MapChecker<K, V> that(Map<K, V> map);

    DoubleChecker that(Double d);

    <T> ClassChecker<T> that(Class<T> aClass);

    <T extends EventObject> EventObjectChecker<T> that(T eventObject);

    <T extends Node> NodeChecker<T> that(T node);

    <T> void that(T t, Matcher<T> hamcrestMatcher);

    <T> void notThat(T t, Matcher<T> hamcrestMatcher);
}
