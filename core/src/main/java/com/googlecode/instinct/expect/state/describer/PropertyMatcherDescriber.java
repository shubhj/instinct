/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public class PropertyMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherUtil matcherUtil = new CommonMatcherUtilImpl();

    private final T actual;
    private final String propertyName;
    private final Class<?> propertyType;

    public PropertyMatcherDescriber(final T actual, final String propertyName, final Class<?> propertyType) {
        checkNotNull(actual, propertyName, propertyType);
        this.actual = actual;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public final String describe() {
        final String expectedProperty = new StringBuilder().
                append(getSimpleClassName()).append(".").append(getGetter()).append(" and/or ").
                append(getSimpleClassName()).append(".").append(getSetter()).toString();
        final StringBuilder builder = new StringBuilder();
        builder.append(newLine()).
                append("Expected: ").append(expectedProperty).append(" should exist.").
            append(newLine()).append(createFiveSpaces()).append("got: ").append(expectedProperty).append(" does not exist.").
                append(newLine());
        return builder.toString();
    }

    private String createFiveSpaces() {
        return matcherUtil.space(5);
    }

    private String getSetter() {
        return matcherUtil.getSetterFor(propertyName, propertyType);
    }

    private String getGetter() {
        return matcherUtil.getGetterFor(propertyName, propertyType);
    }

    private String getSimpleClassName() {
        return matcherUtil.getSimplClassName(actual);
    }

    private String newLine() {
        return matcherUtil.newLine();
    }
}
