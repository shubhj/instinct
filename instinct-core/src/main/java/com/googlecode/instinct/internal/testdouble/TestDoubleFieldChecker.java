package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;

public interface TestDoubleFieldChecker {
    void checkField(Field field, Object instance);
}
