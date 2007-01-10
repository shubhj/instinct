package com.googlecode.instinct.test.mock;

public interface MockCreator {
    <T> MockControl createMockController(Class<T> toMock);
}
