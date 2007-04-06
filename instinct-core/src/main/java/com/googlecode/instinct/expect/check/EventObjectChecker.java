package com.googlecode.instinct.expect.check;

import java.util.EventObject;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Remove the wildcard.")
public interface EventObjectChecker<T extends EventObject> extends ObjectChecker<T> {
    void eventFrom(Class<? extends EventObject> cls, Object object);

    void eventFrom(Object object);

    void notEventFrom(Class<? extends EventObject> aClass, Object object);

    void notEventFrom(Object object);
}
