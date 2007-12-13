/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class PropertyMatcherWithValueDescriber<T> implements MatcherDescriber {

    private final CommonMatcherUtil matcherUtil = new CommonMatcherUtilImpl();
    private final T subject;
    private final String propertyName;
    private final Class<?> propertyType;
    private final Matcher<?> matcher;

    public PropertyMatcherWithValueDescriber(final T subject, final String propertyName, final Class<?> propertyType, final Matcher<?> matcher) {
        this.subject = subject;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.matcher = matcher;
    }

    public final String describe() {
        final String expectedProperty = new StringBuilder().append(getSimplClassName(subject)).append(".").append(getGetter()).toString();
        final StringBuilder builder = new StringBuilder();
        builder.append(newLine()).
                    append("Expected: ").append(expectedProperty).append(" should exist and it should return a value of ").
                    append(getMatcherValue()).append(".").
                append(newLine()).
                    append(createFiveSpaces()).append("got: ").append(expectedProperty).append(" does not exist and/or does not return a value of ").
                    append(getMatcherValue()).append(".").
                append(newLine());
        return builder.toString();
    }

    private String getGetter() {
        return matcherUtil.getGetterFor(propertyName,propertyType);
    }

    private String createFiveSpaces() {
        return matcherUtil.space(5);
    }

    private String newLine() {
        return matcherUtil.newLine();
    }

    private String getMatcherValue() {
        final Description description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

    private String getSimplClassName(final Object instance) {
        return instance.getClass().getSimpleName();
    }
}
