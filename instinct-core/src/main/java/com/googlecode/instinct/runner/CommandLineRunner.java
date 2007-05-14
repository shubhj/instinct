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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Collection;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.runner.CommandLineUsage;
import com.googlecode.instinct.internal.runner.CommandLineUsageImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.ITEM_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ContextResultMessageBuilder;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;

/**
 * Command line specification runner. Runs a context or specification method sending the results to standard out.
 * The format of the arguments are as follows:
 * <pre>
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack
 * $ java CommandLineRunner com.googlecode.instinct.example.stackAnEmptyStack#mustBeEmpty
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack#mustBeEmpty com.googlecode.instinct.example.stack.AnEmptyMagazineRack
 * </pre>
 *
 * @see CommandLineUsage
 */
@Fix({"Write atomic test for this."})
@Suggest("Add formatting options as command line argument.")
@SuppressWarnings({"IOResourceOpenedButNotSafelyClosed", "UseOfSystemOutOrSystemErr"})
public final class CommandLineRunner implements ContextListener {
    private static final CommandLineUsage USAGE = new CommandLineUsageImpl();
    private static final RunnableItemBuilder RUNNABLE_ITEM_BUILDER = new RunnableItemBuilderImpl();
    private static final boolean AUTO_FLUSH_OUTPUT = true;
    private final ContextResultMessageBuilder messageBuilder = BRIEF.getMessageBuilder();
    private final PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)), AUTO_FLUSH_OUTPUT);

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
    }

    @Fix("Use a specification listener to report these as they come out.")
    public void postContextRun(final ContextClass contextClass, final ContextResult contextResult) {
        checkNotNull(contextClass, contextResult);
        writer.println(messageBuilder.buildMessage(contextResult));
    }

    private void run(final String itemsToRun) {
        final Collection<RunnableItem> runnableItems = RUNNABLE_ITEM_BUILDER.build(itemsToRun);
        for (final RunnableItem runnableItem : runnableItems) {
            runnableItem.addContextListener(this);
            runnableItem.run();
        }
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
    private void printUsage() {
        out.println(USAGE.getUsage());
    }

    /**
     * Runs a context or specification method sending the results to standard out.
     *
     * @param args Command line arguments (see above).
     */
    @SuppressWarnings({"LocalVariableOfConcreteClass"})
    public static void main(final String... args) {
        checkNotNull((Object[]) args);
        final CommandLineRunner runner = new CommandLineRunner();
        if (args.length == 0) {
            runner.printUsage();
        } else {
            final String itemsToRun = mergeCommandLineArguments(args);
            runner.run(itemsToRun);
        }
    }

    private static String mergeCommandLineArguments(final String... args) {
        final StringBuilder items = new StringBuilder();
        for (final String arg : args) {
            items.append(arg).append(ITEM_SEPARATOR);
        }
        return items.toString();
    }
}
