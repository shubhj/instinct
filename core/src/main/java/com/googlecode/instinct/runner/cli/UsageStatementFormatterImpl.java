/*
 * Copyright 2008 Jeremy Mawson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.runner.cli;

/**
 * Provides command line friendly CharSequence representations of pertinent objects.
 */
public final class UsageStatementFormatterImpl implements UsageStatementFormatter {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final Integer OPTION_DESC_COL = 26;
    private static final Integer COLUMN_WIDTH = 80;

    public CharSequence format(final OptionArgument argument) {
        if (argument == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder("  -").append(argument.getShortFormat()).append(", --")
                .append(argument.getLongFormat()).append(" ").append(argument.getValueKey());
        return appendToBounds(builder, argument.getDescription(), OPTION_DESC_COL, COLUMN_WIDTH);
    }

    private CharSequence appendToBounds(final StringBuilder builder, final String stringToAppend, final Integer leftCol, final Integer rightCol) {
        int cursor = builder.length();
        final String[] wordsToAppend = stringToAppend.split(" ");
        cursor = padToLeftColumn(builder, leftCol, cursor);
        for (final String word : wordsToAppend) {
            if (cursor + word.length() >= rightCol) {
                builder.append(NEW_LINE);
                cursor = 0;
                cursor = padToLeftColumn(builder, leftCol, cursor);
            }
            cursor += append(builder, " ");
            cursor += append(builder, word);
        }
        return builder.toString();
    }

    private int padToLeftColumn(final StringBuilder builder, final Integer leftCol, final int cursor) {
        int cursorPos = cursor;
        while (cursorPos < leftCol - 1) {
            cursorPos += append(builder, " ");
        }
        return cursorPos;
    }

    private int append(final StringBuilder builder, final String aString) {
        builder.append(aString);
        return aString.length();
    }
}
