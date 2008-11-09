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

import static com.googlecode.instinct.internal.util.Fj.toFjList;
import static com.googlecode.instinct.internal.util.StringUtil.removeFirstNewline;
import static com.googlecode.instinct.internal.util.StringUtil.removeLastNewline;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static java.lang.System.getProperty;

public final class ExceptionUtil {
    private static final int LINES_PER_EXCEPTION = 15;
    private static final boolean AUTO_FLUSH = true;
    private static final String NEW_LINE = getProperty("line.separator");
    private static final String TAB = "\t";
    private static final String TRIMMED_SUFFIX = TAB + "...";

    private ExceptionUtil() {
        throw new UnsupportedOperationException();
    }

    public static String trimStackTrace(final Throwable throwable) {
        return throwable == null ? "" : trimStackTrace(throwable, LINES_PER_EXCEPTION);
    }

    public static String trimStackTrace(final Throwable throwable, final int numberOfLines) {
        if (throwable == null) {
            return "";
        } else {
            final List<String> stackLines = toFjList(stackTrace(throwable).split(NEW_LINE));
            final List<String> trimmedStackTrace = stackLines.take(numberOfLines).snoc(TRIMMED_SUFFIX).intersperse(NEW_LINE);
            return asString(join(trimmedStackTrace.map(new F<String, List<Character>>() {
                public List<Character> f(final String stackTrace) {
                    return fromString(stackTrace);
                }
            })));
        }
    }

    public static String stackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "";
        } else {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(out, AUTO_FLUSH));
            final String stackTrace = out.toString();
            return removeFirstNewline(removeLastNewline(stackTrace));
        }
    }

    public static F<Throwable, String> stackTrace() {
        return new F<Throwable, String>() {
            public String f(final Throwable throwable) {
                return stackTrace(throwable);
            }
        };
    }

    public static F<Throwable, String> message() {
        return new F<Throwable, String>() {
            public String f(final Throwable throwable) {
                if (throwable == null) {
                    return "";
                } else {
                    final String message = throwable.getMessage();
                    return message == null || message.length() == 0 ? message : removeFirstNewline(removeLastNewline(message));
                }
            }
        };
    }
}
