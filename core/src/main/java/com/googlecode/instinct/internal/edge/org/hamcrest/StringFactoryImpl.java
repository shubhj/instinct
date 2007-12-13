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
