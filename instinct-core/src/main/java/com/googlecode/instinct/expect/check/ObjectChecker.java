package com.googlecode.instinct.expect.check;

public interface ObjectChecker<T> {
    void equalTo(T t);

    void notEqualTo(T t);

    void instanceOf(Class<T> aClass);

    void notInstanceOf(Class<T> aClass);

    void sameInstanceAs(T t);

    void notSameInstanceAs(T t);

    void isNull();

    void notNull();

    void hasToString(org.hamcrest.Matcher<String> matcher);

    void matchesAllOf(org.hamcrest.Matcher<T>... matchers);

    void matchesAllOf(Iterable<org.hamcrest.Matcher<T>> iterable);

    void notMatchAllOf(org.hamcrest.Matcher<T>... matchers);

    void notMatchAllOf(Iterable<org.hamcrest.Matcher<T>> iterable);

    void matchesAnyOf(org.hamcrest.Matcher<T>... matchers);

    void matchesAnyOf(Iterable<org.hamcrest.Matcher<T>> iterable);

    void notMatchAnyOf(org.hamcrest.Matcher<T>... matchers);

    void notMatchAnyOf(Iterable<org.hamcrest.Matcher<T>> iterable);

    void hasBeanProperty(String string);

    void hasBeanProperty(String string, org.hamcrest.Matcher matcher);
}
