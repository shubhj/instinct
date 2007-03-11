package com.googlecode.instinct.expect.check;

public interface ArrayChecker<T> extends ObjectChecker<T[]> {
    void containsItem(org.hamcrest.Matcher<T> matcher);

    void containsItem(T t);

    void notContainItem(org.hamcrest.Matcher<T> matcher);

    void notContainItem(T t);

    void hasLength(int length);
}
