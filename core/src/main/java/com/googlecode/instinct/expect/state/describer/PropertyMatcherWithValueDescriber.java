/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberBuilder;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberImpl;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class PropertyMatcherWithValueDescriber<T> implements MatcherDescriber {

    private final CommonMatcherDescriber matcherDescriber = new CommonMatcherDescriberImpl();
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
        final String expectedProperty = new StringBuilder().append(getSimplClassName(subject)).append(".").
                append(matcherDescriber.getGetterFor(propertyName,propertyType)).toString();
        final String matcherValue = getMatcherValue();

        final MatcherDescriberBuilder builder = new MatcherDescriberImpl().
                addNewLine().
                addValue("Expected: ").addValue(expectedProperty).addValue(" should exist and it should return a value of ").
                    addValue(matcherValue).addValue(".").
                addNewLine().
                addSpace(5).addValue("got: ").addValue(expectedProperty).addValue(" does not exist and/or does not return a value of ").
                    addValue(matcherValue).addValue(".").
                addNewLine();
        return builder.describe();
    }

    private String getMatcherValue() {
        final Description description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

    private <S> String getSimplClassName(final S instance) {
        return instance.getClass().getSimpleName();
    }
}
