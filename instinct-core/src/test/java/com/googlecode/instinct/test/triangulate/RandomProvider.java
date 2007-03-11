package com.googlecode.instinct.test.triangulate;

public interface RandomProvider {
    int intInRange(int min, int max);
    <T> T getRandom(Class<T> type);
}
