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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ItemResult;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilder;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ResultMessageBuilder;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import static java.lang.System.getProperty;

@Suggest("Add pending here")
public final class VerboseResultMessageBuilder implements ResultMessageBuilder {
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final String TAB = "\t";
    private static final String SPACER = ", ";
    private static final String NEW_LINE = getProperty("line.separator");
    private final SpecificationFailureMessageBuilder failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();

    public String buildMessage(final ContextClass contextClass) {
        checkNotNull(contextClass);
        return null;
    }

    public String buildMessage(final ContextResult contextResult) {
        checkNotNull(contextResult);
        return buildContextResultMessage(contextResult);
    }

    public String buildMessage(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        return null;
    }

    public String buildMessage(final ContextResultsSummary summary) {
        checkNotNull(summary);
        return String.format("%n%1$d specifications, %2$d failures%nFinished in %3$f seconds", summary.getFailedCount() + summary.getSuccessCount(),
                summary.getFailedCount(), summary.getSecondsTaken());
    }

    public String buildMessage(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        return null;
    }

    private String buildContextResultMessage(final ContextResult contextResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append(getContextSummary(contextResult));
        builder.append(getSpecificationResults(contextResult));
        builder.append(NEW_LINE);
        return builder.toString();
    }

    private String getSpecificationResults(final ContextResult contextResult) {
        return asString(join(contextResult.getSpecificationResults().map(new F<SpecificationResult, List<Character>>() {
            public List<Character> f(final SpecificationResult result) {
                return fromString(TAB + buildSpecificationResultMessage(result));
            }
        })));
    }

    private String getContextSummary(final ContextResult contextResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contextResult.getContextClass().getName()).append(SPACER);
        builder.append("Specifications run: ").append(getNumberOfSpecsRun(contextResult)).append(SPACER);
        builder.append("Successes: ").append(contextResult.getNumberOfSuccesses()).append(SPACER);
        builder.append("Failures: ").append(contextResult.getNumberOfFailures()).append(SPACER);
        builder.append("Total time elapsed: ").append(getExecutionTime(contextResult)).append(" seconds");
        builder.append(NEW_LINE);
        return builder.toString();
    }

    private String buildSpecificationResultMessage(final SpecificationResult specificationResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append(specificationResult.getSpecificationMethod().getName()).append(SPACER);
        builder.append("Time elapsed: ").append(millisToSeconds(specificationResult.getExecutionTime())).append(" seconds").append(SPACER);
        builder.append("Status: ").append(specificationResult.completedSuccessfully() ? "succeeded" : "FAILED");
        if (!specificationResult.completedSuccessfully()) {
            builder.append(NEW_LINE).append(TAB).append("Cause: ");
            builder.append(failureMessageBuilder.buildMessage(specificationResult.getStatus()));
        }
        builder.append(NEW_LINE);
        return builder.toString();
    }

    private int getNumberOfSpecsRun(final ContextResult contextResult) {
        return contextResult.getSpecificationResults().length();
    }

    private double getExecutionTime(final ItemResult contextResult) {
        return millisToSeconds(contextResult.getExecutionTime());
    }

    private double millisToSeconds(final long millis) {
        return (double) millis / MILLISECONDS_IN_SECONDS;
    }
}
