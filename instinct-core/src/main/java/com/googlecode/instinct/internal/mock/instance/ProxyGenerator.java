package com.googlecode.instinct.internal.mock.instance;

import net.sf.cglib.proxy.MethodInterceptor;

public interface  ProxyGenerator {
    <T> Object newProxy(Class<T> classToProxy, MethodInterceptor methodInterceptor);
}
