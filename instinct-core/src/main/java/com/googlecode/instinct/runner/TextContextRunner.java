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

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ContextResultMessageBuilder;
import com.googlecode.instinct.report.PrintWriterStatusLogger;
import com.googlecode.instinct.report.ResultFormat;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import com.googlecode.instinct.report.StatusLogger;

@Fix({"Write atomic test for this.", "Don't make this implement ContextRunner?"})
public final class TextContextRunner implements ContextRunner {
    public static final String METHOD_SEPARATOR = "#";
    private static final boolean AUTO_FLUSH_OUTPUT = true;
    private final ContextRunner contextRunner;

    /**
     * Create a new context runner that sends output to standard out using brief formatting.
     */
    @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
    public TextContextRunner() {
        this(System.out);
    }

    /**
     * Create a new context runner that sends output to the given output stream using brief formatting.
     *
     * @param output The output stream to send results to.
     */
    public TextContextRunner(final OutputStream output) {
        this(output, BRIEF);
    }

    /**
     * Create a new context runner that sends output to the given output stream using the given message builder.
     *
     * @param output The output stream to send results to.
     * @param resultFormat The format the specification results should be printed using.
     */
    public TextContextRunner(final OutputStream output, final ResultFormat resultFormat) {
        checkNotNull(output, resultFormat);
        final ContextRunner runner = new StandardContextRunner();
        final ContextResultMessageBuilder messageBuilder = resultFormat.getMessageBuilder();
        contextRunner = new StatusLoggingContextRunner(runner, messageBuilder, createLogger(output));
    }

    /**
     * Runs the given context.
     *
     * @param contextClass A class containing specifications (a behaviour/specification context).
     * @return The results of running the given context class.
     */
    @Suggest("Add method name? Overloaded? Then invoke the SpecRunner.")
    public <T> ContextResult run(final Class<T> contextClass) {
        checkNotNull(contextClass);
        return contextRunner.run(contextClass);
    }

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private StatusLogger createLogger(final OutputStream output) {
        return new PrintWriterStatusLogger(new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)), AUTO_FLUSH_OUTPUT));
    }

    /**
     * Runs the given contexts sending the results to standard out.
     *
     * @param contextClasses An array of classes containing specifications (a behaviour/specification context) to run.
     */
    public static void runContexts(final Class<?>... contextClasses) {
        final ContextRunner runner = new TextContextRunner();
        for (final Class<?> contextClass : contextClasses) {
            runner.run(contextClass);
        }
    }

    /**
     * Runs a single context or specification method sending the results to standard out.
     * The format of the argument is as follows:
     * <pre>
     * $ TextContextRunner com.googlecode.instinct.example.stack.AnEmptyStack
     * $ TextContextRunner com.googlecode.instinct.example.stackAnEmptyStack#mustBeEmpty
     * </pre>
     *
     * @param args The fully qualified class name of the context to run, with an optional
     */
    @Suggest("Move this implementation elewhere")
    public static void main(final String... args) {
        if (args.length == 1) {
            final ContextRunner contextRunner = new TextContextRunner();
            final Class<?> contextClass = getContextClass(args[0]);
            contextRunner.run(contextClass);
        }
    }

    private static Class<?> getContextClass(final String specificationToRun) {
        final String className = getClassName(specificationToRun);
        return new DefaultEdgeClass().forName(className);
    }

    private static String getClassName(final String specificationToRun) {
        final int index = specificationToRun.indexOf(METHOD_SEPARATOR);
        if (index >= 0) {
            return specificationToRun.substring(0, index);
//            methodName = specificationToRun.substring(index + 1);
        } else {
            return specificationToRun;
        }
    }
}
