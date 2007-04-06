package com.googlecode.instinct.expect.check;

import org.hamcrest.Matcher;

public interface IterableChecker<E, T extends Iterable<E>> extends ObjectChecker<T> {

    void containsItem(E element);

    void containsItem(Matcher<E> matcher);

    void notContainItem(E item);

    void notContainItem(Matcher<E> matcher);

    void containsItems(Matcher<E>... matchers);

    void containsItems(E... items);

    void notContainItems(Matcher<E>... matchers);

    void notContainItems(E... items);
}
