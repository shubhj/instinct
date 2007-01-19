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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class StatusLoggingBehaviourContextRunner implements BehaviourContextRunner {
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final String SPACER = ", ";
    private final BehaviourContextRunner wrappedRunner;
    private final StatusLogger statusLogger;

    public StatusLoggingBehaviourContextRunner(final BehaviourContextRunner wrappedRunner, final StatusLogger statusLogger) {
        checkNotNull(wrappedRunner, statusLogger);
        this.wrappedRunner = wrappedRunner;
        this.statusLogger = statusLogger;
    }

    public <T> BehaviourContextResult run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        final BehaviourContextResult behaviourContextResult = wrappedRunner.run(behaviourContextClass);
        for (final SpecificationResult specificationResult : behaviourContextResult.getSpecificationResults()) {
            statusLogger.log(buildContextSummary(behaviourContextResult));
        }
        return behaviourContextResult;
    }

    @Suggest("Probably shouldn't use \n")
    private String buildContextSummary(final BehaviourContextResult behaviourContextResult) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Behaviour context: ").append(behaviourContextResult.getBehaviourContextName()).append(SPACER);
        builder.append("Specifications run: ").append(getNumberOfSpecsRun(behaviourContextResult)).append(SPACER);
        builder.append("Successes: ").append(behaviourContextResult.getNumberOfSuccesses()).append(SPACER);
        builder.append("Failures: ").append(behaviourContextResult.getNumberOfFailures()).append(SPACER);
        builder.append("Time elapsed: ").append(getExecutionTime(behaviourContextResult)).append(" seconds");
        return builder.toString();
    }

    private int getNumberOfSpecsRun(final BehaviourContextResult behaviourContextResult) {
        return behaviourContextResult.getSpecificationResults().size();
    }

    private double getExecutionTime(final BehaviourContextResult behaviourContextResult) {
        return (double) behaviourContextResult.getExecutionTime() / MILLISECONDS_IN_SECONDS;
    }
}
