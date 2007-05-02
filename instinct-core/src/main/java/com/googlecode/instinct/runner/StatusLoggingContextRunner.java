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

package com.googlecode.instinct.runner;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ContextResultMessageBuilder;
import com.googlecode.instinct.report.StatusLogger;

@Suggest("Pull out a text logger that logs to std out.")
public final class StatusLoggingContextRunner implements ContextRunner {
    private final ContextRunner delegate;
    private final ContextResultMessageBuilder messageBuilder;
    private final StatusLogger statusLogger;

    public StatusLoggingContextRunner(final ContextRunner delegate, final ContextResultMessageBuilder messageBuilder,
            final StatusLogger statusLogger) {
        checkNotNull(delegate, messageBuilder, statusLogger);
        this.delegate = delegate;
        this.messageBuilder = messageBuilder;
        this.statusLogger = statusLogger;
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
    }

    @Suggest("This logging should be done on the fly by the runner, potentially removing the need for this class.")
    public ContextResult run(final ContextClass contextClass) {
        checkNotNull(contextClass);
        final ContextResult contextResult = delegate.run(contextClass);
        logResults(contextResult);
        return contextResult;
    }

    private void logResults(final ContextResult contextResult) {
        final String message = messageBuilder.buildMessage(contextResult);
        statusLogger.log(message);
    }
}
