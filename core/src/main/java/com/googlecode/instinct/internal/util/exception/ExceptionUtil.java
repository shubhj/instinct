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
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import static java.lang.System.getProperty;

public final class ExceptionUtil {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final int LINES_PER_EXCEPTION = 10;

    private ExceptionUtil() {
        throw new UnsupportedOperationException();
    }

    public static String trimStackTrace(final Throwable throwable) {
        return trimStackTrace(throwable, LINES_PER_EXCEPTION);
    }

    public static String trimStackTrace(final Throwable throwable, final int numberOfLines) {
        final List<String> trimmedStackTrace = toFjList(stackTraceToString(throwable).split(NEW_LINE)).take(numberOfLines);
        return asString(join(trimmedStackTrace.map(new F<String, List<Character>>() {
            public List<Character> f(final String stackTrace) {
                return fromString(stackTrace);
            }
        })));
    }

    public static String stackTraceToString(final Throwable throwable) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintWriter(out));
        return out.toString();
    }
}
