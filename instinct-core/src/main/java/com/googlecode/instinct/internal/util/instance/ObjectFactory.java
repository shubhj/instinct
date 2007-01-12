package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Add type inference of the objects.",
        "Get the constructor of the concrete type where the number of arguments matches the passed in values.",
        "Then work out if the objects are of compatible types."})
public interface ObjectFactory {
    <T> T create(Class<T> concreteClass);

    <T> T create(Class<T> concreteClass, Class<?>[] types, Object[] values);
}
