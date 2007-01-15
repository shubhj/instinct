package com.googlecode.instinct.internal.mock;

public interface TestDoubleHolder {
    void addControl(TestDoubleControl control, Object testDouble);

    TestDoubleControl getController(Object testDouble);

    Object getDouble(TestDoubleControl control);
}
