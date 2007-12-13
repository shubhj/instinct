/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import org.hamcrest.Matcher;

public interface CommonMatcherDescriber<T> {
    String describeValue(T value);

    String describeExpectation(Matcher<T> matcher);

    String getGetterFor(String propertyName, Class<?> propertyType);

    String getSetterFor(String propertyName, Class<?> propertyType);

    String getSimplClassName(Object instance);
}
