package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class DummyTestDoubleCreator implements TestDoubleCreator {
    public Object createValue(final Field field) {
        checkNotNull(field);
        return new Object();
    }
}
