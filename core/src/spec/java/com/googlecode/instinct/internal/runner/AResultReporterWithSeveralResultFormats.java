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
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.report.ResultFormat;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.runner.ResultReporter;
import fj.data.List;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Map;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@SuppressWarnings({"TooBroadScope"})
public final class AResultReporterWithSeveralResultFormats {
    private static final EnumSet<ResultFormat> RESULT_FORMATS = EnumSet.allOf(ResultFormat.class);
    @Subject private ResultReporter reporter;
    @Mock ContextClass contextClass;
    @Mock ContextResult contextResult;
    @Mock SpecificationMethod specificationMethod;
    @Mock SpecificationResult specificationResult;
    @Mock PrintWriterDecorator printWriterDecorator;
    @Mock ResultMessageBuilder resultMessageBuilder;
    //    @Mock PrintWriterDecorator xmlPrintWriterDecorator;
    //    @Mock ResultMessageBuilder xmlResultMessageBuilder;
    //    @Mock PrintWriterDecorator briefPrintWriterDecorator;
    //    @Mock ResultMessageBuilder briefResultMessageBuilder;
    //    @Mock PrintWriterDecorator verbosePrintWriterDecorator;
    //    @Mock ResultMessageBuilder verboseResultMessageBuilder;

    @BeforeSpecification
    @SuppressWarnings({"unchecked"})
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        reporter = new ResultReporterImpl(List.<ResultFormat>list(RESULT_FORMATS.toArray(new ResultFormat[RESULT_FORMATS.size()])));
        // inject mock objects into this instance
        final Field writersField = ResultReporterImpl.class.getDeclaredField("writers");
        final Field buildersField = ResultReporterImpl.class.getDeclaredField("builders");
        writersField.setAccessible(true);
        buildersField.setAccessible(true);
        final Map<ResultFormat, PrintWriterDecorator> writers = (Map<ResultFormat, PrintWriterDecorator>) writersField.get(reporter);
        final Map<ResultFormat, ResultMessageBuilder> builders = (Map<ResultFormat, ResultMessageBuilder>) buildersField.get(reporter);
        for (final ResultFormat format : RESULT_FORMATS) {
            writers.put(format, printWriterDecorator);
            builders.put(format, resultMessageBuilder);
        }
        //        writers.put(XML, xmlPrintWriterDecorator);
        //        writers.put(BRIEF, briefPrintWriterDecorator);
        //        writers.put(VERBOSE, verbosePrintWriterDecorator);
        //        builders.put(XML, xmlResultMessageBuilder);
        //        builders.put(BRIEF, briefResultMessageBuilder);
        //        builders.put(VERBOSE, verboseResultMessageBuilder);
        writersField.setAccessible(false);
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
                exactly(RESULT_FORMATS.size()).of(resultMessageBuilder).buildMessage(contextClass);
                will(returnValue(message));
                exactly(RESULT_FORMATS.size()).of(printWriterDecorator).printBefore(contextClass, message);
            }
        });
        reporter.preContextRun(contextClass);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPostContextRun() {
        final String message = "afterContextMessage";
        expect.that(new Expectations() {
            {
                exactly(RESULT_FORMATS.size()).of(resultMessageBuilder).buildMessage(contextResult);
                will(returnValue(message));
                exactly(RESULT_FORMATS.size()).of(printWriterDecorator).printAfter(contextResult, message);
            }
        });
        reporter.postContextRun(contextResult);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPreSpecificationMethod() {
        final String message = "beforeSpecificationMessage";
        expect.that(new Expectations() {
            {
                exactly(RESULT_FORMATS.size()).of(resultMessageBuilder).buildMessage(specificationMethod);
                will(returnValue(message));
                exactly(RESULT_FORMATS.size()).of(printWriterDecorator).printBefore(specificationMethod, message);
            }
        });
        reporter.preSpecificationMethod(specificationMethod);
    }

    @Specification
    public void willCallMessageBuilderAndPrintWriterOnPostSpecificationMethod() {
        final String message = "afterSpecificationMessage";
        expect.that(new Expectations() {
            {
                exactly(RESULT_FORMATS.size()).of(resultMessageBuilder).buildMessage(specificationResult);
                will(returnValue(message));
                exactly(RESULT_FORMATS.size()).of(printWriterDecorator).printAfter(specificationResult, message);
            }
        });
        reporter.postSpecificationMethod(specificationResult);
    }
}