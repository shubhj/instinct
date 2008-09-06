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
import com.googlecode.instinct.report.ResultFormat;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.runner.ResultReporter;
import fj.Effect;
import fj.data.List;
import java.util.EnumMap;
import java.util.Map;

public final class ResultReporterImpl implements ResultReporter {
    private static final PrintWriterDecoratorFactory PRINT_WRITER_DECORATOR_FACTORY = new PrintWriterDecoratorFactoryImpl();
    private static final ResultMessageBuilderFactory RESULT_MESSAGE_BUILDER_FACTORY = new ResultMessageBuilderFactoryImpl();
    private final List<ResultFormat> resultFormats;
    private final Map<ResultFormat, PrintWriterDecorator> writers = new EnumMap<ResultFormat, PrintWriterDecorator>(ResultFormat.class);
    private final Map<ResultFormat, ResultMessageBuilder> builders = new EnumMap<ResultFormat, ResultMessageBuilder>(ResultFormat.class);

    public ResultReporterImpl(final List<ResultFormat> resultFormats) {
        this.resultFormats = resultFormats;
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                writers.put(format, PRINT_WRITER_DECORATOR_FACTORY.createFor(format));
                builders.put(format, RESULT_MESSAGE_BUILDER_FACTORY.createFor(format));
            }
        });
    }

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                final String message = builders.get(format).buildMessage(contextClass);
                writers.get(format).printBefore(contextClass, message);
            }
        });
    }

    public void postContextRun(final ContextResult contextResult) {
        checkNotNull(contextResult);
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                final String message = builders.get(format).buildMessage(contextResult);
                writers.get(format).printAfter(contextResult, message);
            }
        });
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                final String message = builders.get(format).buildMessage(specificationMethod);
                writers.get(format).printBefore(specificationMethod, message);
            }
        });
    }

    public void postSpecificationMethod(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                final String message = builders.get(format).buildMessage(specificationResult);
                writers.get(format).printAfter(specificationResult, message);
            }
        });
    }

    public void printSummary(final ContextResultsSummary summary) {
        resultFormats.foreach(new Effect<ResultFormat>() {
            public void e(final ResultFormat format) {
                final String message = builders.get(format).buildMessage(summary);
                writers.get(format).printAfterAll(message);
            }
        });
    }
}
