/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberBuilder;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriberImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public class PropertyMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherDescriber matcherDescriber = new CommonMatcherDescriberImpl();
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
        final String expectedProperty = new StringBuilder().append(matcherDescriber.getSimplClassName(actual)).append(".").append(
                matcherDescriber.getGetterFor(propertyName, propertyType)).append(" and/or ").append(matcherDescriber.getSimplClassName(actual)).
                append(".").append(matcherDescriber.getSetterFor(propertyName, propertyType)).toString();
        final MatcherDescriberBuilder builder = new MatcherDescriberImpl().
                addNewLine().
                addValue("Expected: ").addValue(expectedProperty).addValue(" should exist.").
                addNewLine().
                addSpace(5).addValue("got: ").addValue(expectedProperty).addValue(" does not exist.").
                addNewLine();
        return builder.describe();
    }
}
