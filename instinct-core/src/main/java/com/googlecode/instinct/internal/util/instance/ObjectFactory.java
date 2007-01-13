package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.internal.util.Suggest;

public interface ObjectFactory {
    @Suggest("Remove this method.")
            <T> T create(Class<T> concreteClass);

    <T> T create(Class<T> concreteClass, Object... constructorArgumentValues);

    <T, U> T create(Class<T> concreteClass, Class<U>[] types, Object[] values);
}
