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
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.report.ResultMessageBuilderFactory;
import com.googlecode.instinct.internal.report.ResultMessageBuilderFactoryImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.Fj.toFjArray;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ResultFormat;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import com.googlecode.instinct.report.ResultMessageBuilder;
import fj.Effect;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public final class TextRunner implements ContextRunner, ContextListener {
    private static final ResultMessageBuilderFactory RESULT_MESSAGE_BUILDER_FACTORY = new ResultMessageBuilderFactoryImpl();
    private static final boolean AUTO_FLUSH_OUTPUT = true;
    private final PrintWriter writer;
    private final ResultMessageBuilder messageBuilder;

    /**
     * Create a new context runner that sends output to standard out using brief formatting.
     */
    @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
    public TextRunner() {
        this(System.out);
    }

    /**
     * Create a new context runner that sends output to the given output stream using brief formatting.
     * @param output The output stream to send results to.
     */
    public TextRunner(final OutputStream output) {
        this(output, BRIEF);
    }

    /**
     * Create a new context runner that sends output to the given output stream using the given message builder.
     * @param output The output stream to send results to.
     * @param resultFormat The format the specification results should be printed using.
     */
    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    public TextRunner(final OutputStream output, final ResultFormat resultFormat) {
        checkNotNull(output, resultFormat);
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)), AUTO_FLUSH_OUTPUT);
        messageBuilder = RESULT_MESSAGE_BUILDER_FACTORY.createFor(new Formatter(resultFormat));
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
    }

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
    }

    @Fix("Use a specification listener to report these as they come out.")
    public void postContextRun(final ContextResult contextResult) {
        checkNotNull(contextResult);
        final String contextResultMessage = messageBuilder.buildMessage(contextResult);
        if (contextResultMessage != null && contextResultMessage.trim().length() > 0) {
            writer.println(contextResultMessage);
        }
    }

    /**
     * Runs the specifications in the given context returning the result.
     * @param contextClass The context class containing specifications to run.
     */
    public ContextResult run(final ContextClass contextClass) {
        checkNotNull(contextClass);
        contextClass.addContextListener(this);
        return contextClass.run();
    }

    /**
     * Runs the specifications in the given contexts, sending the results to standard out using brief formatting..
     * @param contextClasses An array of classes containing specifications to run.
     */
    public static void runContexts(final Class<?>... contextClasses) {
        checkNotNull((Object[]) contextClasses);
        final ContextRunner runner = new TextRunner();
        toFjArray(contextClasses).foreach(new Effect<Class<?>>() {
            public void e(final Class<?> context) {
                runner.run(new ContextClassImpl(context));
            }
        });
    }
}
