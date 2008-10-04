/*
 * Copyright 2008 Jeremy Mawson
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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.report.ContextResultsSummary;
import com.googlecode.instinct.internal.report.PrintWriterDecorator;
import com.googlecode.instinct.internal.report.PrintWriterDecoratorFactory;
import com.googlecode.instinct.internal.report.PrintWriterDecoratorFactoryImpl;
import com.googlecode.instinct.internal.report.ResultMessageBuilderFactory;
import com.googlecode.instinct.internal.report.ResultMessageBuilderFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.runner.ResultReporter;
import fj.Effect;
import fj.data.HashMap;
import fj.data.List;
import fj.pre.Equal;
import fj.pre.Hash;

public final class ResultReporterImpl implements ResultReporter {
    private static final PrintWriterDecoratorFactory PRINT_WRITER_DECORATOR_FACTORY = new PrintWriterDecoratorFactoryImpl();
    private static final ResultMessageBuilderFactory RESULT_MESSAGE_BUILDER_FACTORY = new ResultMessageBuilderFactoryImpl();
    private final HashMap<Formatter, PrintWriterDecorator> writers =
            new HashMap<Formatter, PrintWriterDecorator>(Equal.<Formatter>anyEqual(), Hash.<Formatter>anyHash());
    private final HashMap<Formatter, ResultMessageBuilder> builders =
            new HashMap<Formatter, ResultMessageBuilder>(Equal.<Formatter>anyEqual(), Hash.<Formatter>anyHash());
    private final List<Formatter> formatters;

    public ResultReporterImpl(final List<Formatter> formatters) {
        this.formatters = formatters;
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                writers.set(formatter, PRINT_WRITER_DECORATOR_FACTORY.createFor(formatter));
                builders.set(formatter, RESULT_MESSAGE_BUILDER_FACTORY.createFor(formatter));
            }
        });
    }

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                final String message = builders.get(formatter).some().buildMessage(contextClass);
                writers.get(formatter).some().printBefore(contextClass, message);
            }
        });
    }

    public void postContextRun(final ContextResult contextResult) {
        checkNotNull(contextResult);
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                final String message = builders.get(formatter).some().buildMessage(contextResult);
                writers.get(formatter).some().printAfter(contextResult, message);
            }
        });
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                final String message = builders.get(formatter).some().buildMessage(specificationMethod);
                writers.get(formatter).some().printBefore(specificationMethod, message);
            }
        });
    }

    public void postSpecificationMethod(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                final String message = builders.get(formatter).some().buildMessage(specificationResult);
                writers.get(formatter).some().printAfter(specificationResult, message);
            }
        });
    }

    public void printSummary(final ContextResultsSummary summary) {
        formatters.foreach(new Effect<Formatter>() {
            public void e(final Formatter formatter) {
                final String message = builders.get(formatter).some().buildMessage(summary);
                writers.get(formatter).some().printAfterAll(message);
            }
        });
    }
}
