/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

public final class StringFactoryImpl implements StringFactory {
    private static final String NL = System.getProperty("line.separator");


    public String space() {
        return space(1);
    }

    public String space(final int spaces) {
        final StringBuilder builder = new StringBuilder();
        for (int x = 0; x < spaces; x++) {
            builder.append(" ");
        }

        return builder.toString();
    }

    public String newLine() {
        return createNewLine(1);
    }

    public String quoted(final String value) {
        return "\"" + value + "\"";
    }

    public String createNewLine(final int lines) {
        final StringBuilder builder = new StringBuilder();
        for (int x = 0; x < lines; x++) {
            builder.append(NL);
        }

        return builder.toString();
    }
}
