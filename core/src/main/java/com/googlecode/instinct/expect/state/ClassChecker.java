package com.googlecode.instinct.expect.state;

public interface ClassChecker<T> extends ObjectChecker<Class<T>> {
    <U> void typeCompatibleWith(Class<U> cls);

    <U> void notTypeCompatibleWith(Class<U> cls);
}
