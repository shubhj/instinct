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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.exception.ExceptionUtil.stackTrace;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnExceptionUtil {
    @Stub(auto = false) private Exception exception;

    @BeforeSpecification
    public void before() {
        try {
            throw new RuntimeException("An exception is thrown");
        } catch (RuntimeException e) {
            exception = e;
        }
    }

    @Specification
    public void convertsAStackTraceToString() {
        final String stackTrace = stackTrace(exception);
        expect.that(stackTrace).containsString("java.lang.RuntimeException: An exception is thrown");
        expect.that(stackTrace).containsString("\tat " + AnExceptionUtil.class.getName() + ".before(");
    }

    @Specification
    public void canTrimAStackTrace() {
        final String[] lines = ExceptionUtil.trimStackTrace(exception, 10).split("\n");
        expect.that(lines).isOfSize(11);
    }
}
