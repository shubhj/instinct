package com.googlecode.instinct.internal.mock;

import java.util.HashMap;
import java.util.Map;

public final class TestDoubleHolderImpl implements TestDoubleHolder {
    private final Map<Object, TestDoubleControl> mockToControlMap = new HashMap<Object, TestDoubleControl>();

    public void addControl(final TestDoubleControl control, final Object testDouble) {
        mockToControlMap.put(testDouble, control);
    }

    public TestDoubleControl getController(final Object testDouble) {
        return mockToControlMap.get(testDouble);
    }

    public Object getDouble(final TestDoubleControl control) {
        for (final Object mockedObject : mockToControlMap.keySet()) {
            if (mockToControlMap.get(mockedObject).equals(control)) {
                return mockedObject;
            }
        }
        throw new UnkownTestDoubleException("Unable to find test double for control " + control);
    }
}
