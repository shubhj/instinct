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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.internal.util.ListUtil.stringToCharacter;
import static com.googlecode.instinct.internal.util.StringUtil.unindent;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiserImpl;
import com.googlecode.instinct.internal.util.exception.ExceptionUtil;
import static com.googlecode.instinct.internal.util.exception.ExceptionUtil.stackTrace;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import static java.lang.System.getProperty;

@SuppressWarnings({"NonSerializableFieldInSerializableClass"})
public final class AggregatingException extends RuntimeException {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final long serialVersionUID = -3212899060420825954L;
    private final ExceptionSanitiser exceptionSanitiser;
    private final String message;
    private final List<Throwable> errors;

    public AggregatingException(final String message, final List<Throwable> errors) {
        this.message = message;
        this.errors = errors;
        exceptionSanitiser = new ExceptionSanitiserImpl();
    }

    public List<Throwable> getAggregatedErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return errors.isEmpty() ? getMessage() : getMessage() + NEW_LINE + NEW_LINE + summary() + NEW_LINE + NEW_LINE + details();
    }

    private String summary() {
        final String summaryPreamble = "Summary: " + errors.length() + " error" + (errors.length() == 1 ? "" : "s") + " occurred";
        final String summary = text(errors, ExceptionUtil.message(), NEW_LINE);
        return summaryPreamble + NEW_LINE + formatSummary(StringUtil.indentEachLine(summary));
    }

    private String details() {
        final String detailPreamble = "Full details follow:";
        final String details = text(errors, stackTrace(), NEW_LINE + NEW_LINE);
        return detailPreamble + NEW_LINE + NEW_LINE + details;
    }

    private String formatSummary(final String summary) {
        final List<String> lines = Fj.toFjList(summary.split(NEW_LINE));
        return asString(join(lines.map(new F<String, List<Character>>() {
            public List<Character> f(final String line) {
                return fromString(line.matches("^\\s+[0-9]+\\).*") ? unindent(line) : line);
            }
        }).intersperse(fromString(NEW_LINE))));
    }

    private String text(final List<Throwable> errors, final F<Throwable, String> text, final String delimiter) {
        return asString(join(throwableToString(errors, List.<String>nil(), 1, text).intersperse(fromString(delimiter))));
    }

    private List<List<Character>> throwableToString(final List<Throwable> errors, final List<String> strings, final int exceptionNumber,
            final F<Throwable, String> text) {
        if (errors.isEmpty()) {
            return strings.reverse().map(stringToCharacter());
        } else {
            final String message = exceptionNumber + (exceptionNumber >= 10 ? ") " : ")  ") + text.f(exceptionSanitiser.sanitise(errors.head()));
            return throwableToString(errors.tail(), strings.cons(message), exceptionNumber + 1, text);
        }
    }
}
