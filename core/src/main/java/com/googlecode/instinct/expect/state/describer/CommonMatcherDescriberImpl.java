/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.ClassUtil;
import com.googlecode.instinct.internal.util.ClassUtilImpl;

public final class CommonMatcherDescriberImpl<T> implements CommonMatcherDescriber<T> {

    private final ClassUtil classUtil = new ClassUtilImpl();

    public String describeValue(final T value) {
        final StringDescription description = new StringDescription();
        description.appendValue(value);
        return description.toString();
    }

    public String describeExpectation(final Matcher<T> matcher) {
        final StringDescription description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

    public String getGetterFor(final String propertyName, final Class<?> propertyType) {
        checkNotNull(propertyName);
        String prefix = "get";
        if (classUtil.isBooleanType(propertyType)) {
            prefix = "is";
        }
        return prefix + classUtil.capitalizeFirstCharacter(propertyName) + "()";
    }

    public String getSetterFor(final String propertyName, final Class<?> propertyType) {
        checkNotNull(propertyName);
        return "set" + classUtil.capitalizeFirstCharacter(propertyName) + "(" + propertyType.getSimpleName() + ")";
    }

    public String getSimplClassName(final Object instance) {
        return instance.getClass().getSimpleName();
    }
}
