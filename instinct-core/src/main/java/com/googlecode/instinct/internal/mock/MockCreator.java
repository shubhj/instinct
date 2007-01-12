package com.googlecode.instinct.internal.mock;

public interface MockCreator {
    <T> MockControl createMockController(Class<T> toMock);

    <T> MockControl createMockController(Class<T> toMock, String roleName);
}
