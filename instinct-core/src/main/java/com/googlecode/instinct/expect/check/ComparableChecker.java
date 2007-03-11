package com.googlecode.instinct.expect.check;

public interface ComparableChecker<T extends Comparable<T>> extends ObjectChecker<T> {
    void greaterThan(T comparable);

    void greaterThanOrEqualTo(T comparable);

    void lessThan(T comparable);

    void lessThanOrEqualTo(T comparable);
}
