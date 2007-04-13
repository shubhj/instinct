package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matcher;

public interface ObjectChecker<T> {
    void equalTo(T t);

    void notEqualTo(T t);

    void instanceOf(Class<T> cls);

    void notInstanceOf(Class<T> cls);

    void sameInstanceAs(T t);

    void notSameInstanceAs(T t);

    void isNull();

    void notNull();

    void hasToString(Matcher<String> matcher);

    void matchesAllOf(Matcher<T>... matchers);

    void matchesAllOf(Iterable<Matcher<T>> iterable);

    void notMatchAllOf(Matcher<T>... matchers);

    void notMatchAllOf(Iterable<Matcher<T>> iterable);

    void matchesAnyOf(Matcher<T>... matchers);

    void matchesAnyOf(Iterable<Matcher<T>> iterable);

    void notMatchAnyOf(Matcher<T>... matchers);

    void notMatchAnyOf(Iterable<Matcher<T>> iterable);

    void hasBeanProperty(String string);

    @Suggest("Can we type this?")
    void hasBeanProperty(String string, Matcher matcher);
}
