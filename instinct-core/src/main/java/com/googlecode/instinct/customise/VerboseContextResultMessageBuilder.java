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

package com.googlecode.instinct.customise;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import static java.lang.System.getProperty;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class VerboseContextResultMessageBuilder implements ContextResultMessageBuilder {
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final String TAB = "\t";
    private static final String SPACER = ", ";
    private static final String NEW_LINE = getProperty("line.separator");

    public String buildMessage(final BehaviourContextResult behaviourContextResult) {
        checkNotNull(behaviourContextResult);
        final StringBuilder builder = new StringBuilder();
        appendSummary(builder, behaviourContextResult);
        appendSpecifications(builder, behaviourContextResult);
        builder.append(NEW_LINE);
        return builder.toString();
    }

    private void appendSpecifications(final StringBuilder builder, final BehaviourContextResult behaviourContextResult) {
        for (final SpecificationResult specificationResult : behaviourContextResult.getSpecificationResults()) {
            appendSpecification(builder, specificationResult);
        }
    }

    private void appendSummary(final StringBuilder builder, final BehaviourContextResult behaviourContextResult) {
        builder.append("Behaviour context: ").append(behaviourContextResult.getBehaviourContextName()).append(SPACER);
        builder.append("Specifications run: ").append(getNumberOfSpecsRun(behaviourContextResult)).append(SPACER);
        builder.append("Successes: ").append(behaviourContextResult.getNumberOfSuccesses()).append(SPACER);
        builder.append("Failures: ").append(behaviourContextResult.getNumberOfFailures()).append(SPACER);
        builder.append("Total time elapsed: ").append(getExecutionTime(behaviourContextResult)).append(" seconds");
        builder.append(NEW_LINE);
    }

    private void appendSpecification(final StringBuilder builder, final SpecificationResult specificationResult) {
        builder.append(TAB).append("Specification ").append(specificationResult.getSpecificationName()).append(SPACER);
        builder.append("time elapsed: ").append(millisToSeconds(specificationResult.getExecutionTime())).append(" seconds").append(SPACER);
        builder.append("status: ").append(specificationResult.completedSuccessfully() ? "succeeded" : "FAILED");
        if (!specificationResult.completedSuccessfully()) {
            builder.append(NEW_LINE).append(TAB).append("Cause: ");
            appendFailureCause(specificationResult.getStatus(), builder);
        }
        builder.append(NEW_LINE);
    }

    private void appendFailureCause(final SpecificationRunStatus status, final StringBuilder builder) {
        // Note. The nesting is deep as we are going through reflection via an edge.
        final Throwable failureCause = ((Throwable) status.getDetailedStatus()).getCause().getCause();
        final String stackTrace = getFailureStackTrace(failureCause);
        final String s = stackTrace.replace(TAB, TAB + TAB);
        builder.append(s);
    }

    // DEBT GenericIllegalRegexp {
    private String getFailureStackTrace(final Throwable failureCause) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        failureCause.printStackTrace(new PrintWriter(out, true));
        return out.toString();
    }
    // } DEBT GenericIllegalRegexp

    private int getNumberOfSpecsRun(final BehaviourContextResult behaviourContextResult) {
        return behaviourContextResult.getSpecificationResults().size();
    }

    private double getExecutionTime(final BehaviourContextResult behaviourContextResult) {
        return millisToSeconds(behaviourContextResult.getExecutionTime());
    }

    private double millisToSeconds(final long millis) {
        return (double) millis / MILLISECONDS_IN_SECONDS;
    }
}
