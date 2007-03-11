package com.googlecode.instinct.expect.check;

import java.util.EventObject;

public interface EventObjectChecker<T extends EventObject> extends ObjectChecker<T> {
    void eventFrom(Class<? extends EventObject> aClass, Object object);

    void eventFrom(Object object);

    void notEventFrom(Class<? extends EventObject> aClass, Object object);

    void notEventFrom(Object object);
}
