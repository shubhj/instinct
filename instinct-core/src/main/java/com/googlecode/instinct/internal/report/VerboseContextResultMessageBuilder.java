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

package com.googlecode.instinct.internal.report;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import static java.lang.System.getProperty;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ContextResultMessageBuilder;

public final class VerboseContextResultMessageBuilder implements ContextResultMessageBuilder {
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final String TAB = "\t";
    private static final String SPACER = ", ";
    private static final String NEW_LINE = getProperty("line.separator");
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();

    public String buildMessage(final ContextResult contextResult) {
        checkNotNull(contextResult);
        final StringBuilder builder = new StringBuilder();
        appendSummary(builder, contextResult);
        appendSpecifications(builder, contextResult);
        builder.append(NEW_LINE);
        return builder.toString();
    }

    private void appendSpecifications(final StringBuilder builder, final ContextResult contextResult) {
        for (final SpecificationResult specificationResult : contextResult.getSpecificationResults()) {
            appendSpecification(builder, specificationResult);
        }
    }

    private void appendSummary(final StringBuilder builder, final ContextResult contextResult) {
        builder.append(contextResult.getBehaviourContextName()).append(SPACER);
        builder.append("Specifications run: ").append(getNumberOfSpecsRun(contextResult)).append(SPACER);
        builder.append("Successes: ").append(contextResult.getNumberOfSuccesses()).append(SPACER);
        builder.append("Failures: ").append(contextResult.getNumberOfFailures()).append(SPACER);
        builder.append("Total time elapsed: ").append(getExecutionTime(contextResult)).append(" seconds");
        builder.append(NEW_LINE);
    }

    private void appendSpecification(final StringBuilder builder, final SpecificationResult specificationResult) {
        builder.append(TAB).append(specificationResult.getSpecificationName()).append(SPACER);
        builder.append("Time elapsed: ").append(millisToSeconds(specificationResult.getExecutionTime())).append(" seconds").append(SPACER);
        builder.append("Status: ").append(specificationResult.completedSuccessfully() ? "succeeded" : "FAILED");
        if (!specificationResult.completedSuccessfully()) {
            builder.append(NEW_LINE).append(TAB).append("Cause: ");
            appendFailureCause(specificationResult.getStatus(), builder);
        }
        builder.append(NEW_LINE);
    }

    private void appendFailureCause(final SpecificationRunStatus status, final StringBuilder builder) {
        final Throwable rootCause = exceptionFinder.getRootCause((Throwable) status.getDetailedStatus());
        final String stackTrace = getFailureStackTrace(rootCause);
        final String s = stackTrace.replace(TAB, TAB + TAB);
        builder.append(s);
    }

    // SUPPRESS GenericIllegalRegexp {
    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private String getFailureStackTrace(final Throwable failureCause) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        failureCause.printStackTrace(new PrintWriter(out, true));
        return out.toString();
    }
    // } SUPPRESS GenericIllegalRegexp

    private int getNumberOfSpecsRun(final ContextResult contextResult) {
        return contextResult.getSpecificationResults().size();
    }

    private double getExecutionTime(final ContextResult contextResult) {
        return millisToSeconds(contextResult.getExecutionTime());
    }

    private double millisToSeconds(final long millis) {
        return (double) millis / MILLISECONDS_IN_SECONDS;
    }
}
