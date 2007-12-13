/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.ClassUtil;
import com.googlecode.instinct.internal.util.ClassUtilImpl;
import com.googlecode.instinct.internal.edge.org.hamcrest.StringFactory;
import com.googlecode.instinct.internal.edge.org.hamcrest.StringFactoryImpl;

public final class CommonMatcherUtilImpl<T> implements CommonMatcherUtil<T> {

    private final ClassUtil classUtil = new ClassUtilImpl();
    private final StringFactory stringFactory = new StringFactoryImpl();

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

    public String newLine() {
        return stringFactory.newLine();
    }

    public String space(final int spaces) {
        return stringFactory.space(5);
    }
}
