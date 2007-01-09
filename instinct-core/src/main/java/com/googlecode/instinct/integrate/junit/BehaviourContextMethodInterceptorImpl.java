package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeMethod;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeMethod;
import junit.framework.Test;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class BehaviourContextMethodInterceptorImpl implements MethodInterceptor {
    private EdgeClass edgeClass = new DefaultEdgeClass();
    private EdgeMethod edgeMethod = new DefaultEdgeMethod();
    private final Test delegate;

    public BehaviourContextMethodInterceptorImpl(final Test delegate) {
        this.delegate = delegate;
    }

    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) {
        final Method invokee = edgeClass.getMethod(delegate.getClass(), method.getName(), method.getParameterTypes());
        return edgeMethod.invoke(invokee, delegate, args);
    }
}
