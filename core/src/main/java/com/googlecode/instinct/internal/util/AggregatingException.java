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
        return message + "; " + errors.length() + " error(s) occurred" + NEW_LINE + NEW_LINE + errorsAsString(errors);
    }

    private String errorsAsString(final List<Throwable> errors) {
        return asString(join(errorAsString(errors, List.<String>nil(), 1).intersperse(fromString(NEW_LINE + NEW_LINE))));
    }

    private List<List<Character>> errorAsString(final List<Throwable> errors, final List<String> traces, final int exceptionNumber) {
        if (errors.isEmpty()) {
            return traces.reverse().map(new F<String, List<Character>>() {
                public List<Character> f(final String trace) {
                    return fromString(trace);
                }
            });
        } else {
            final String trace = exceptionNumber + ") " + stackTrace(exceptionSanitiser.sanitise(errors.head()));
            return errorAsString(errors.tail(), traces.cons(trace), exceptionNumber + 1);
        }
    }
}
