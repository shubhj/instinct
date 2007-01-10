package com.googlecode.instinct.test.mock;

import org.jmock.Mock;

public interface MockControllerHolder {
    void addControl(Mock control, Object mockedObject);

    Mock getMockController(Object mockedObject);

    Object getMock(Mock mockController);
}
