/*
 * Copyright 2006-2008 Tom Adams
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

import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.report.ContextResultsSummary;
import com.googlecode.instinct.internal.runner.CommandLineUsage;
import com.googlecode.instinct.internal.runner.CommandLineUsageImpl;
import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.internal.runner.ItemResult;
import com.googlecode.instinct.internal.runner.ResultFormatBuilder;
import com.googlecode.instinct.internal.runner.ResultFormatBuilderImpl;
import com.googlecode.instinct.internal.runner.ResultReporterImpl;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.Fj.toFjList;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.cli.OptionArgument;
import com.googlecode.instinct.runner.cli.Settings;
import com.googlecode.instinct.runner.cli.SettingsFactory;
import com.googlecode.instinct.runner.cli.SettingsFactoryImpl;
import fj.Effect;
import fj.data.List;
import static java.lang.System.out;

/**
 * Command line specification runner. Runs a context or specification method sending the results to standard out. The format of the arguments are:
 * <pre>
 * $ java CommandLineRunner [options] &lt;context_class[#spec]&gt; [&lt;context_class[#spec]&gt;]...
 * </pre>
 * For example:
 * <pre>
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack
 * $ java CommandLineRunner -f BRIEF,XML com.googlecode.instinct.example.stack.AnEmptyStack
 * $ java CommandLineRunner com.googlecode.instinct.example.stackAnEmptyStack#mustBeEmpty
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack#mustBeEmpty com.googlecode.instinct.example.stack.AnEmptyMagazineRack
 * </pre>
 * @see CommandLineUsage
 */
@Fix("Why is there this & the text runner? This should use text runner")
@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class CommandLineRunner {
    private static final CommandLineUsage USAGE = new CommandLineUsageImpl();
    private static final RunnableItemBuilder RUNNABLE_ITEM_BUILDER = new RunnableItemBuilderImpl();
    private static final ResultFormatBuilder RESULT_FORMAT_BUILDER = new ResultFormatBuilderImpl();
    private static final SettingsFactory SETTINGS_FACTORY = new SettingsFactoryImpl();

    private CommandLineRunner() {
    }

    /**
     * Runs a context or specification method sending the results to standard out.
     * @param args Command line arguments (see above).
     */
    public static void main(final String... args) {
        checkNotNull((Object[]) args);
        checkNotEmpty(args);
        final CommandLineRunner runner = new CommandLineRunner();
        final Settings settings = SETTINGS_FACTORY.extractFrom(toFjList(args));
        final List<Formatter> resultFormats = RESULT_FORMAT_BUILDER.build(settings.getOption(OptionArgument.FORMATTERS));
        final List<RunnableItem> runnableItems = RUNNABLE_ITEM_BUILDER.build(settings.getArguments());
        final ResultReporter reporter = new ResultReporterImpl(resultFormats);
        final ExitCode exitCode = runner.run(runnableItems, reporter);
        System.exit(exitCode.getCode());
    }

    private static void checkNotEmpty(final String... args) {
        if (args.length == 0) {
            out.println(USAGE.getUsage());
            System.exit(ExitCode.ERROR_NO_ARGUMENTS.getCode());
        }
    }

    private ExitCode run(final List<RunnableItem> itemsToRun, final ResultReporter reporter) {
        final ContextResultsSummary summary = new ContextResultsSummary();
        itemsToRun.foreach(new Effect<RunnableItem>() {
            public void e(final RunnableItem item) {
                item.addContextListener(reporter);
                item.addSpecificationListener(reporter);
                final ItemResult itemResult = item.run();
                if (itemResult.completedSuccessfully()) {
                    summary.recordSuccess(itemResult.getExecutionTime());
                } else {
                    summary.recordFailure(itemResult.getExecutionTime());
                }
            }
        });
        reporter.printSummary(summary);
        return summary.getFailedCount() == 0 ? ExitCode.SUCCESS : ExitCode.TEST_FAILURES;
    }
}
