package com.googlecode.instinct.test.mock;

public interface MockHolder {
    void addControl(MockControl mockControl, Object mockedObject);

    MockControl getMockController(Object mockedObject);

    Object getMock(MockControl mockControl);
}
