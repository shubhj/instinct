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

import com.googlecode.instinct.internal.runner.ContextResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ContextResultMessageBuilder;

public final class BriefContextResultMessageBuilder implements ContextResultMessageBuilder {
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final String SPACER = ", ";

    public String buildMessage(final ContextResult contextResult) {
        checkNotNull(contextResult);
        return buildContextSummary(contextResult);
    }

    private String buildContextSummary(final ContextResult contextResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Context: ").append(contextResult.getBehaviourContextName()).append(SPACER);
        builder.append("Specifications run: ").append(getNumberOfSpecsRun(contextResult)).append(SPACER);
        builder.append("Successes: ").append(contextResult.getNumberOfSuccesses()).append(SPACER);
        builder.append("Failures: ").append(contextResult.getNumberOfFailures()).append(SPACER);
        builder.append("Time elapsed: ").append(getExecutionTime(contextResult)).append(" seconds");
        return builder.toString();
    }

    private int getNumberOfSpecsRun(final ContextResult contextResult) {
        return contextResult.getSpecificationResults().size();
    }

    private double getExecutionTime(final ContextResult contextResult) {
        return (double) contextResult.getExecutionTime() / MILLISECONDS_IN_SECONDS;
    }
}
