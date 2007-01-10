package com.googlecode.instinct.test.mock;

import java.util.HashMap;
import java.util.Map;
import org.jmock.Mock;

public final class MockControllerHolderImpl implements MockControllerHolder {
    private final Map<Object, Mock> mockToControlMap = new HashMap<Object, Mock>();

    public void addControl(final Mock mockController, final Object mockedObject) {
        mockToControlMap.put(mockedObject, mockController);
    }

    public Mock getMockController(final Object mockedObject) {
        return mockToControlMap.get(mockedObject);
    }

    public Object getMock(final Mock mockController) {
        for (final Object mockedObject : mockToControlMap.keySet()) {
            if (mockToControlMap.get(mockedObject).equals(mockController)) {
                return mockedObject;
            }
        }
        throw new UnkownMockException("Unable to find mock for control " + mockController);
    }
}
