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

import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;

/**
 * Command line specification runner. Runs a context or specification method sending the results to standard out.
 * The format of the arguments are as follows:
 * <pre>
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack
 * $ java CommandLineRunner com.googlecode.instinct.example.stackAnEmptyStack#mustBeEmpty
 * $ java CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack#mustBeEmpty com.googlecode.instinct.example.stack.AnEmptyMagazineRack
 * </pre>
 */
@Fix({"Write atomic test for this."})
public final class CommandLineRunner {
    public static final String METHOD_SEPARATOR = "#";

    private void run(final Class<?> contextClass) {
        TextContextRunner.runContexts(contextClass);
    }

    /**
     * Runs a context or specification method sending the results to standard out.
     *
     * @param args Command line arguments (see above).
     */
    public static void main(final String... args) {
        if (args.length == 0) {
            printUsage();
        } else {
            final Class<?> contextClass = getContextClass(args[0]);
            new CommandLineRunner().run(contextClass);
        }
    }

    // SUPPRESS GenericIllegalRegexp {
    @SuppressWarnings({"UseOfSystemOutOrSystemErr"})
    @Suggest("Support formatting. Support more than one context.")
    private static void printUsage() {
        final String className = CommandLineRunner.class.getSimpleName();
        System.out.println("Usage: " + className + /*" -format <brief|verbose>" +*/ " <context#spec>");
    }
    // } SUPPRESS GenericIllegalRegexp

    @SuppressWarnings({"unchecked"})
    private static <T> Class<T> getContextClass(final String specificationToRun) {
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
