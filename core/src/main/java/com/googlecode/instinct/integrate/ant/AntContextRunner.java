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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import java.util.regex.Pattern;

@Suggest({"Remove this class, refactor Ant runner to not use it, make Ant runner receive the callback and write in real time."})
public final class AntContextRunner implements ContextRunner {
    private static final Pattern NEWLINE = Pattern.compile("\n");
    private final ContextRunner delegate;
    private final ResultMessageBuilder messageBuilder;
    private final StatusLogger statusLogger;

    public AntContextRunner(final ContextRunner delegate, final ResultMessageBuilder messageBuilder,
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

    @Suggest("This logging should be done on the fly by the ant task, potentially removing the need for this class.")
    public ContextResult run(final ContextClass contextClass) {
        checkNotNull(contextClass);
        final ContextResult contextResult = delegate.run(contextClass);
        logResults(contextResult);
        return contextResult;
    }

    private void logResults(final ContextResult contextResult) {
        final String message = messageBuilder.buildMessage(contextResult);
        if (message.trim().length() > 0) {
            final String[] lines = NEWLINE.split(message);
            for (final String line : lines) {
                statusLogger.log(line);
            }
        }
    }
}
