/*
 * Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.util.exception;

import static java.lang.System.getProperty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;

public final class StringUtil {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final Pattern START_OF_LINE = compile("^", MULTILINE);
    private static final String INDENT = "\t";

    private StringUtil() {
        throw new UnsupportedOperationException();
    }

    public static String indentEachLine(final CharSequence text) {
        final Matcher matcher = START_OF_LINE.matcher(text);
        return matcher.replaceAll(INDENT);
    }

    public static String indentEachLineButFirst(final CharSequence text) {
        return removeFirst(indentEachLine(text), INDENT);
    }

    public static String removeFirstNewline(final String text) {
        return removeFirst(text, NEW_LINE);
    }

    @SuppressWarnings({"TypeMayBeWeakened"})
    public static String removeFirst(final String text, final String toRemove) {
        return text.startsWith(toRemove) ? text.substring(1) : text;
    }

    public static String removeLastNewline(final String text) {
        return removeLast(text, NEW_LINE);
    }

    @SuppressWarnings({"TypeMayBeWeakened"})
    public static String removeLast(final String text, final String toRemove) {
        return text.endsWith(toRemove) ? text.substring(0, text.length() - 1) : text;
    }
}
