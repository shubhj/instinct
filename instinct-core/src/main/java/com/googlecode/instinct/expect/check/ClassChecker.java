package com.googlecode.instinct.expect.check;

public interface ClassChecker<T> extends ObjectChecker<Class<T>> {
    void typeCompatibleWith(Class<?> aClass);

    void notTypeCompatibleWith(Class<?> aClass);
}
