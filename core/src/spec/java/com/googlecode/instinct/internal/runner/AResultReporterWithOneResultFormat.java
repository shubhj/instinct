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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.report.PrintWriterDecorator;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.runner.ResultReporter;
import fj.data.HashMap;
import fj.data.List;
import java.lang.reflect.Field;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@SuppressWarnings({"TooBroadScope"})
public final class AResultReporterWithOneResultFormat {

    @Subject private ResultReporter reporter;
    @Mock ContextClass contextClass;
    @Mock ContextResult contextResult;
    @Mock SpecificationMethod specificationMethod;
    @Mock SpecificationResult specificationResult;
    @Mock PrintWriterDecorator printWriterDecorator;
    @Mock ResultMessageBuilder resultMessageBuilder;

    @BeforeSpecification
    @SuppressWarnings({"unchecked"})
    public void setUp() throws IllegalAccessException {
        reporter = new ResultReporterImpl(List.<Formatter>single(new Formatter(BRIEF)));
        // inject mock objects into this instance
        final Field writersField = getFieldByName(ResultReporterImpl.class, "writers");
        writersField.setAccessible(true);
        final HashMap<Formatter, PrintWriterDecorator> writers = (HashMap<Formatter, PrintWriterDecorator>) writersField.get(reporter);
        writers.set(new Formatter(BRIEF), printWriterDecorator);
        writersField.setAccessible(false);
        final Field buildersField = getFieldByName(ResultReporterImpl.class, "builders");
        buildersField.setAccessible(true);
        final HashMap<Formatter, ResultMessageBuilder> builders = (HashMap<Formatter, ResultMessageBuilder>) buildersField.get(reporter);
        builders.set(new Formatter(BRIEF), resultMessageBuilder);
        buildersField.setAccessible(false);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPreContextRun() {
        reporter.preContextRun(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPostContextRun() {
        reporter.postContextRun(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPreSpecificationMethod() {
        reporter.preSpecificationMethod(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPostSpecificationMethod() {
        reporter.postSpecificationMethod(null);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPreContextRun() {
        final String message = "beforeContextMessage";
        expect.that(new Expectations() {
            {
                one(resultMessageBuilder).buildMessage(contextClass);
                will(returnValue(message));
                one(printWriterDecorator).printBefore(contextClass, message);
            }
        });
        reporter.preContextRun(contextClass);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPostContextRun() {
        final String message = "afterContextMessage";
        expect.that(new Expectations() {
            {
                one(resultMessageBuilder).buildMessage(contextResult);
                will(returnValue(message));
                one(printWriterDecorator).printAfter(contextResult, message);
            }
        });
        reporter.postContextRun(contextResult);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPreSpecificationMethod() {
        final String message = "beforeSpecificationMessage";
        expect.that(new Expectations() {
            {
                one(resultMessageBuilder).buildMessage(specificationMethod);
                will(returnValue(message));
                one(printWriterDecorator).printBefore(specificationMethod, message);
            }
        });
        reporter.preSpecificationMethod(specificationMethod);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPostSpecificationMethod() {
        final String message = "afterSpecificationMessage";
        expect.that(new Expectations() {
            {
                one(resultMessageBuilder).buildMessage(specificationResult);
                will(returnValue(message));
                one(printWriterDecorator).printAfter(specificationResult, message);
            }
        });
        reporter.postSpecificationMethod(specificationResult);
    }
}