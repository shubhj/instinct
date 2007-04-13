package com.googlecode.instinct.expect.state;

import org.hamcrest.Matcher;

public interface ArrayChecker<T> extends ObjectChecker<T[]> {
    void containsItem(Matcher<T> matcher);

    void containsItem(T t);

    void notContainItem(Matcher<T> matcher);

    void notContainItem(T t);

    void hasLength(int length);
}
