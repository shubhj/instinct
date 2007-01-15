package com.googlecode.instinct.internal.mock;

public interface MockCreator {
    <T> TestDoubleControl createController(Class<T> toMock);

    <T> TestDoubleControl createController(Class<T> toMock, String roleName);
}
