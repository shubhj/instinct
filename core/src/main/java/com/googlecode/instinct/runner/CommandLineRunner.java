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
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.CommandLineUsage;
import com.googlecode.instinct.internal.runner.CommandLineUsageImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ItemResult;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.ITEM_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import com.googlecode.instinct.report.ResultMessageBuilder;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Collection;

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
@Suggest({"Add formatter/message builder as command line argument.",
        "Can the formatting be moved into the BriefResultMessageBuilder?", "Make this not implement spec listener",
        "Why is there this & the commane line runner? This should use text runner"})
@SuppressWarnings({"IOResourceOpenedButNotSafelyClosed", "UseOfSystemOutOrSystemErr"})
public final class CommandLineRunner implements ContextListener, SpecificationListener {
    private static final CommandLineUsage USAGE = new CommandLineUsageImpl();
    private static final RunnableItemBuilder RUNNABLE_ITEM_BUILDER = new RunnableItemBuilderImpl();
    private static final double MILLISECONDS_IN_SECONDS = 1000.0;
    private static final boolean AUTO_FLUSH_OUTPUT = true;
    private final ResultMessageBuilder messageBuilder = BRIEF.getMessageBuilder();
    private final PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)), AUTO_FLUSH_OUTPUT);

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
        writer.println(contextClass.getName());
    }

    public void postContextRun(final ContextClass contextClass, final ContextResult contextResult) {
        checkNotNull(contextClass, contextResult);
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
    }

    public void postSpecificationMethod(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        checkNotNull(specificationMethod, specificationResult);
        final String message = messageBuilder.buildMessage(specificationResult);
        if (message.trim().length() != 0) {
            writer.println("- " + message);
        }
    }

    private void run(final String itemsToRun) {
        long totalTime = 0L;
        int numberSuccess = 0;
        int numberFailed = 0;
        final Collection<RunnableItem> runnableItems = RUNNABLE_ITEM_BUILDER.build(itemsToRun);
        for (final RunnableItem runnableItem : runnableItems) {
            runnableItem.addContextListener(this);
            runnableItem.addSpecificationListener(this);
            final ItemResult itemResult = runnableItem.run();
            totalTime += itemResult.getExecutionTime();
            if (itemResult.completedSuccessfully()) {
                numberSuccess++;
            } else {
                numberFailed++;
            }
        }
        writer.println();
        writer.println((numberSuccess + numberFailed) + " specifications, " + numberFailed + " failures");
        writer.println("Finished in " + millisToSeconds(totalTime) + " seconds");
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
    private void printUsage() {
        out.println(USAGE.getUsage());
    }

    @Suggest("Utility.")
    private double millisToSeconds(final long millis) {
        return (double) millis / MILLISECONDS_IN_SECONDS;
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
