package com.googlecode.instinct.mock;

import java.util.HashMap;
import java.util.Map;

public final class MockHolderImpl implements MockHolder {
    private final Map<Object, MockControl> mockToControlMap = new HashMap<Object, MockControl>();

    public void addControl(final MockControl mockControl, final Object mockedObject) {
        mockToControlMap.put(mockedObject, mockControl);
    }

    public MockControl getMockController(final Object mockedObject) {
        return mockToControlMap.get(mockedObject);
    }

    public Object getMock(final MockControl mockControl) {
        for (final Object mockedObject : mockToControlMap.keySet()) {
            if (mockToControlMap.get(mockedObject).equals(mockControl)) {
                return mockedObject;
            }
        }
        throw new UnkownMockException("Unable to find mock for control " + mockControl);
    }
}
