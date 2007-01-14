package com.googlecode.instinct.internal.util.instance;

public interface ObjectFactory {
    <T> T create(Class<T> concreteClass, Object... constructorArgumentValues);

    <T> T create(Class<T> concreteClass, Class<?>[] constructorArgumentTypes, Object[] constructorArgumentValues);
}
