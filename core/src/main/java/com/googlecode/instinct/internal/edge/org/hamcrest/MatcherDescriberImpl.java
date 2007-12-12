/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

public class MatcherDescriberImpl implements MatcherDescriber {
    private static final int TAB_SIZE = 4;
    private static final String NL = System.getProperty("line.separator");

    private final StringBuilder builder = new StringBuilder();

    public final MatcherDescriber setReason(final String reason) {
        builder.append(reason);
        return this;
    }

    public final MatcherDescriber setExpectedLabelName(final String expectedLabelName) {
        builder.append(expectedLabelName);
        return this;
    }

    public final MatcherDescriber setExpectedValue(final String labelValue) {
        builder.append(labelValue);
        return this;
    }

    public final MatcherDescriber setReturnedLabelName(final String labelName) {
        builder.append(labelName);
        return this;
    }

    public final MatcherDescriber setReturnedValue(final String returnedValue) {
        builder.append(returnedValue);
        return this;
    }

    public final MatcherDescriber addValue(final String string) {
        builder.append(string);
        return this;
    }

    public final MatcherDescriber addSpace() {
        return addSpace(1);
    }

    public final MatcherDescriber addSpace(final int spaces) {
        for (int x = 0; x < spaces; x++) {
            builder.append(" ");
        }

        return this;
    }

    public final MatcherDescriber addTab() {
        return addSpace(TAB_SIZE);
    }

    public final MatcherDescriber addColon() {
        return addValue(":");
    }

    public final MatcherDescriber addDash() {
        return addValue("-");
    }

    public final MatcherDescriber addEquals() {
        return addValue("=");
    }

    public final MatcherDescriber addUnderscore() {
        return addValue("_");
    }

    public final MatcherDescriber addNewLine() {
        return addNewLine(1);
    }

    public final MatcherDescriber addNewLine(final int lines) {
        for (int x = 0; x < lines; x++) {
            builder.append(NL);
        }

        return this;
    }

    @Override
    public final String toString() {
        return builder.toString();
    }
}
