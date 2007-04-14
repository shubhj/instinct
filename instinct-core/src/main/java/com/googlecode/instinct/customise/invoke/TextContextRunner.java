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

package com.googlecode.instinct.customise.invoke;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

@Fix("Write atomic test for this.")
public final class TextContextRunner implements BehaviourContextRunner {
    private final BehaviourContextRunner contextRunner;

    public TextContextRunner(final OutputStream output, final ContextResultMessageBuilder messageBuilder) {
        checkNotNull(output, messageBuilder);
        contextRunner = new StatusLoggingContextRunner(new BehaviourContextRunnerImpl(), messageBuilder, createLogger(output));
    }

    public <T> BehaviourContextResult run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        return contextRunner.run(behaviourContextClass);
    }

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private StatusLogger createLogger(final OutputStream output) {
        return new PrintWriterStatusLogger(new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)), true));
    }
}
