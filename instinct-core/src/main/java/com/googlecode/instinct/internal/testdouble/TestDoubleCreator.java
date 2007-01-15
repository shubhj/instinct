package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;

public interface TestDoubleCreator {
    Object createValue(Field field);
}
