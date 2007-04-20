package com.googlecode.instinct.expect.state;

import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

/**
 * State-based expectation API.
 * State expectations are expectations on the state of an object, and are similar to JUnit's Assert capabilities.
 * The instinct state-based expectation API is built on top of the Hamcrest Matcher API. The Instinct wrapper methods enable:
 * <ol>
 * <li>IDE code completion reduced to the methods that apply to the type object being checked.</li>
 * <li>A more english like (and longer) flow. </li>
 * <li>The standard Hamcrest Matchers or custom Matcher implementations to be passed in as well.</li>
 * </ol>
 */
public interface StateExpectations {
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
