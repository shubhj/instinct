package com.googlecode.instinct.mock;

public interface MockCreator {
    <T> MockControl createMockController(Class<T> toMock);

    <T> MockControl createMockController(Class<T> toMock, String roleName);
}
