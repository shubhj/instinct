package com.googlecode.instinct.expect.check;

public interface IterableChecker<E, T extends Iterable<E>> extends ObjectChecker<T> {

    void containsItem(E element);

    void containsItem(org.hamcrest.Matcher<E> matcher);

    void notContainItem(E item);

    void notContainItem(org.hamcrest.Matcher<E> matcher);

    void containsItems(org.hamcrest.Matcher<E>... matchers);

    void containsItems(E... items);

    void notContainItems(org.hamcrest.Matcher<E>... matchers);

    void notContainItems(E... items);
}
