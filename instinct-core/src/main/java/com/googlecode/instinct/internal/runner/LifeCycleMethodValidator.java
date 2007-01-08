package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;

interface LifeCycleMethodValidator {
    void checkMethodHasNoParameters(Method method);
    <T> void checkContextConstructor(Class<T> cls);
}
