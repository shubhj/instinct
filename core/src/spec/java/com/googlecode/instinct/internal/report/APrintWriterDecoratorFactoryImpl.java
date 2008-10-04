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

package com.googlecode.instinct.internal.report;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.report.ResultFormat.QUIET;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import static com.googlecode.instinct.report.ResultFormat.XML;
import java.io.File;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class APrintWriterDecoratorFactoryImpl {

    @Subject private PrintWriterDecoratorFactory factory;

    @BeforeSpecification
    public void setUp() {
        factory = new PrintWriterDecoratorFactoryImpl();
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForBriefResultFormats() {
        expect.that(factory.createFor(new Formatter(BRIEF))).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForQuietResultFormats() {
        expect.that(factory.createFor(new Formatter(QUIET))).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForVerboseResultFormats() {
        expect.that(factory.createFor(new Formatter(VERBOSE))).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForXmlResultFormats() {
        expect.that(factory.createFor(new Formatter(XML))).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnAFilePerContextPrintWriterDecoratorForBriefResultFormatsWithToDirSet() {
        final File dir = new File(".");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(BRIEF, dir));
        expect.that(writerDecorator).isAnInstanceOf(FilePerContextPrintWriterDecorator.class);
        expect.that(((FilePerContextPrintWriterDecorator) writerDecorator).getDir()).isEqualTo(dir);
    }

    @Specification
    public void willReturnAFilePerContextPrintWriterDecoratorForQuietResultFormatsWithToDirSet() {
        final File dir = new File(".");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(QUIET, dir));
        expect.that(writerDecorator).isAnInstanceOf(FilePerContextPrintWriterDecorator.class);
        expect.that(((FilePerContextPrintWriterDecorator) writerDecorator).getDir()).isEqualTo(dir);
    }

    @Specification
    public void willReturnAFilePerContextPrintWriterDecoratorForVerboseResultFormatsWithToDirSet() {
        final File dir = new File(".");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(VERBOSE, dir));
        expect.that(writerDecorator).isAnInstanceOf(FilePerContextPrintWriterDecorator.class);
        expect.that(((FilePerContextPrintWriterDecorator) writerDecorator).getDir()).isEqualTo(dir);
    }

    @Specification
    public void willReturnAFilePerContextPrintWriterDecoratorForXmlResultFormatsWithToDirSet() {
        final File dir = new File(".");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(XML, dir));
        expect.that(writerDecorator).isAnInstanceOf(FilePerContextPrintWriterDecorator.class);
        expect.that(((FilePerContextPrintWriterDecorator) writerDecorator).getDir()).isEqualTo(dir);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForBriefResultFormatsWithToDirSetWithNonDir() {
        final File dir = new File("/not/a/valid/path");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(BRIEF, dir));
        expect.that(writerDecorator).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForQuietResultFormatsWithToDirSetWithNonDir() {
        final File dir = new File("/not/a/valid/path");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(QUIET, dir));
        expect.that(writerDecorator).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForVerboseResultFormatsWithToDirSetWithNonDir() {
        final File dir = new File("/not/a/valid/path");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(VERBOSE, dir));
        expect.that(writerDecorator).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification
    public void willReturnASystemOutPrintWriterDecoratorForXmlResultFormatsWithToDirSetWithNonDir() {
        final File dir = new File("/not/a/valid/path");
        final PrintWriterDecorator writerDecorator = factory.createFor(new Formatter(XML, dir));
        expect.that(writerDecorator).isAnInstanceOf(SystemOutPrintWriterDecorator.class);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptANullResultFormat() {
        factory.createFor(null);
    }
}