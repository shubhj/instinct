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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.runner.CommandLineRunner;
import com.googlecode.instinct.runner.cli.OptionArgument;
import com.googlecode.instinct.runner.cli.UsageStatementFormatter;
import com.googlecode.instinct.runner.cli.UsageStatementFormatterImpl;

public final class CommandLineUsageImpl implements CommandLineUsage {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final UsageStatementFormatter FORMATTER = new UsageStatementFormatterImpl();

    public CharSequence getUsage() {
        final StringBuilder usage = new StringBuilder();
        final String className = CommandLineRunner.class.getSimpleName();
        usage.append("Usage: ").append(className).append(" [options] <context_class[#spec]> [<context_class[#spec>]]...").append(NEWLINE);
        usage.append(NEWLINE);
        usage.append("Runs specifications, sending results to standard out.").append(NEWLINE);
        usage.append("  - To run a single specification - include the fully qualified class name of").append(NEWLINE);
        usage.append("    the class containing the specification, followed by a specification method").append(NEWLINE);
        usage.append("    name, separated by a '#'.").append(NEWLINE);
        usage.append("  - To run all the specifications in a class - include the fully qualified").append(NEWLINE);
        usage.append("    name of the class containing the specification.").append(NEWLINE);
        usage.append(NEWLINE);
        usage.append("You can list as many specifications as you like.").append(NEWLINE);
        usage.append(NEWLINE);
        usage.append("Options:");
        usage.append(NEWLINE);
        usage.append(FORMATTER.format(OptionArgument.FORMATTERS));
        return usage.toString();
    }
}
