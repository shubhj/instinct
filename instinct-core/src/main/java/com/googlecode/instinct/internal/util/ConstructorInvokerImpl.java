package com.googlecode.instinct.internal.util;

import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;

public final class ConstructorInvokerImpl implements ConstructorInvoker {
    private final EdgeClass edgeClass = new DefaultEdgeClass();

    public <T> Object invokeNullaryConstructor(final Class<T> cls) {
        return edgeClass.newInstance(cls);
    }
}
