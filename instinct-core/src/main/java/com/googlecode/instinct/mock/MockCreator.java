package com.googlecode.instinct.mock;

public interface MockCreator {
    <T> MockControl createMockController(Class<T> toMock);
}
