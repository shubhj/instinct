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

import com.googlecode.instinct.internal.util.exception.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiserImpl;
import static com.googlecode.instinct.internal.util.exception.ExceptionUtil.trimStackTrace;
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
    private final List<Throwable> errors;

    public AggregatingException(final List<Throwable> errors) {
        this.errors = errors;
        exceptionSanitiser = new ExceptionSanitiserImpl();
    }

    @Override
    public String getMessage() {
        return errorsAsString(errors);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + NEW_LINE + getMessage();
    }

    private String errorsAsString(final List<Throwable> errors) {
        final List<List<Character>> shortenedStackTraces = errors.map(new F<Throwable, List<Character>>() {
            @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
            public List<Character> f(final Throwable throwable) {
                return fromString(throwable + trimStackTrace(exceptionSanitiser.sanitise(throwable)));
            }
        });
        return asString(join(shortenedStackTraces.intersperse(fromString(NEW_LINE + NEW_LINE))));
    }
}
