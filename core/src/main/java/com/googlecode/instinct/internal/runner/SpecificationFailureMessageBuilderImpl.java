/*
 * Copyright 2006-2007 Tom Adams
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

package com.googlecode.instinct.internal.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class SpecificationFailureMessageBuilderImpl implements SpecificationFailureMessageBuilder {
    private static final String NO_FAILURE = "";
    private static final boolean AUTO_FLUSH = true;
    private ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();

    public String buildMessage(final SpecificationRunStatus status) {
        checkNotNull(status);
        return status.runSuccessful() ? NO_FAILURE : buildFailureMessage(status);
    }

    private String buildFailureMessage(final SpecificationRunStatus status) {
        final Throwable rootCause = exceptionFinder.getRootCause((Throwable) status.getDetailedStatus());
        return getStackTrace(rootCause);
    }

    private String getStackTrace(final Throwable rootCause) {
        final ByteArrayOutputStream byteArrayOutputStream = objectFactory.create(ByteArrayOutputStream.class);
        final PrintWriter printWriter = objectFactory.create(PrintWriter.class, byteArrayOutputStream, AUTO_FLUSH);
        writeStackTrace(rootCause, printWriter);
        return byteArrayOutputStream.toString();
    }

    // SUPPRESS GenericIllegalRegexp {
    private void writeStackTrace(final Throwable rootCause, final PrintWriter printWriter) {
        rootCause.printStackTrace(printWriter);
    }
    // } SUPPRESS GenericIllegalRegexp
}
