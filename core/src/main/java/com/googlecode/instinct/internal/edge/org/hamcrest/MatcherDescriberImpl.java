/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.edge.org.hamcrest;

import com.googlecode.instinct.internal.util.Fix;

@Fix("Simplify this class.")
public final class MatcherDescriberImpl implements MatcherDescriberBuilder {
    private static final int TAB_SIZE = 4;
    private static final String NL = System.getProperty("line.separator");
    private final StringBuilder builder = new StringBuilder();

    public MatcherDescriberBuilder setReason(final String reason) {
        builder.append(reason);
        return this;
    }

    public MatcherDescriberBuilder setExpectedLabelName(final String expectedLabelName) {
        builder.append(expectedLabelName);
        return this;
    }

    public MatcherDescriberBuilder setExpectedValue(final String labelValue) {
        builder.append(labelValue);
        return this;
    }

    public MatcherDescriberBuilder setReturnedLabelName(final String labelName) {
        builder.append(labelName);
        return this;
    }

    public MatcherDescriberBuilder setReturnedValue(final String returnedValue) {
        builder.append(returnedValue);
        return this;
    }

    public MatcherDescriberBuilder addValue(final String string) {
        builder.append(string);
        return this;
    }

    public MatcherDescriberBuilder addSpace() {
        return addSpace(1);
    }

    public MatcherDescriberBuilder addSpace(final int spaces) {
        for (int x = 0; x < spaces; x++) {
            builder.append(" ");
        }

        return this;
    }

    public MatcherDescriberBuilder addTab() {
        return addSpace(TAB_SIZE);
    }

    public MatcherDescriberBuilder addColon() {
        return addValue(":");
    }

    public MatcherDescriberBuilder addDash() {
        return addValue("-");
    }

    public MatcherDescriberBuilder addEquals() {
        return addValue("=");
    }

    public MatcherDescriberBuilder addUnderscore() {
        return addValue("_");
    }

    public MatcherDescriberBuilder addNewLine() {
        return addNewLine(1);
    }

    public MatcherDescriberBuilder addNewLine(final int lines) {
        for (int x = 0; x < lines; x++) {
            builder.append(NL);
        }

        return this;
    }

    @Override
    public String toString() {
        return describe();
    }

    public String describe() {
        return builder.toString();
    }
}
