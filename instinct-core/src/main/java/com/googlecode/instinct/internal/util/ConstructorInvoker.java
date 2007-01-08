package com.googlecode.instinct.internal.util;

public interface ConstructorInvoker {
    <T> Object invokeNullaryConstructor(Class<T> cls);
}
