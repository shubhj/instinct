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

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class TextContextRunner implements BehaviourContextRunner {
    private final BehaviourContextRunner contextRunner;

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    public TextContextRunner(final ContextResultMessageBuilder messageBuilder, final OutputStream output) {
        checkNotNull(messageBuilder, output);
        final StatusLogger statusLogger = new PrintWriterStatusLogger(new PrintWriter(new BufferedWriter(new OutputStreamWriter(output))));
        contextRunner = new StatusLoggingContextRunner(new BehaviourContextRunnerImpl(), messageBuilder, statusLogger);
    }

    public <T> BehaviourContextResult run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        return contextRunner.run(behaviourContextClass);
    }
}