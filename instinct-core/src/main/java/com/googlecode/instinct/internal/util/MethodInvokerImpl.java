package com.googlecode.instinct.internal.util;

import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeMethod;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeMethod;

public final class MethodInvokerImpl implements MethodInvoker {
    private static final Object[] NO_PARAMS = {};
    private final EdgeMethod edgeMethod = new DefaultEdgeMethod();

    public void invokeMethod(final Object instance, final Method method) {
        method.setAccessible(true);
        edgeMethod.invoke(method, instance, NO_PARAMS);
    }
}
