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

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.report.ResultFormat.QUIET;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import static com.googlecode.instinct.report.ResultFormat.XML;
import fj.data.List;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AResultFormatBuilderImpl {
    @Subject
    private ResultFormatBuilder resultFormatBuilder;

    @BeforeSpecification
    public void setUp() {
        resultFormatBuilder = new ResultFormatBuilderImpl();
    }

    @Specification
    public void willReturnTheDefaultListOfRunnersIfInputParameterIsNull() {
        final List<Formatter> formatList = resultFormatBuilder.build(null);
        assertNotNull(formatList);
        assertEquals(1, formatList.length());
        assertEquals(new Formatter(BRIEF), formatList.head());
    }

    @Specification
    public void willReturnNoFormattersIfInputParameterIsEmpty() {
        final List<Formatter> formatList = resultFormatBuilder.build("");
        assertNotNull(formatList);
        assertTrue(formatList.isEmpty());
    }

    @Specification
    public void willReturnNoFormattersIfInputParameterIsWhitespace() {
        final List<Formatter> formatList = resultFormatBuilder.build(" \t\n ");
        assertNotNull(formatList);
        assertTrue(formatList.isEmpty());
    }

    @Specification
    public void willReturnASingleFormatterWhenDeclared() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml");
        assertNotNull(formatList);
        assertEquals(1, formatList.length());
        assertEquals(new Formatter(XML), formatList.head());
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Could not parse formatters list \"xlm\"")
    public void willFailToParseAnUnknownFormat() {
        resultFormatBuilder.build("xlm");
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Could not parse formatters list \"brief,xml,quite\"")
    public void willFailToParseListContainingAnUnknownFormat() {
        resultFormatBuilder.build("brief,xml,quite");
    }

    @Specification
    public void willReturnAListOfFormattersInSequenceWhenDeclared() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml,quiet,verbose,brief");
        assertNotNull(formatList);
        assertEquals(4, formatList.length());
        assertEquals(new Formatter(XML), formatList.index(0));
        assertEquals(new Formatter(QUIET), formatList.index(1));
        assertEquals(new Formatter(VERBOSE), formatList.index(2));
        assertEquals(new Formatter(BRIEF), formatList.index(3));
    }

    @Specification
    public void willParseToDirOptionOnFormatter() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml{toDir=/tmp}");
        assertNotNull(formatList);
        assertEquals(1, formatList.length());
        assertEquals(new Formatter(XML, new File("/tmp")), formatList.head());
    }

    @Specification
    public void willParseToDirOptionOnMultipleFormatters() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml{toDir=/tmp},verbose{toDir=/home}");
        assertNotNull(formatList);
        assertEquals(2, formatList.length());
        assertEquals(new Formatter(XML, new File("/tmp")), formatList.index(0));
        assertEquals(new Formatter(VERBOSE, new File("/home")), formatList.index(1));
    }

    @Specification
    public void willIgnoreUnknownParameters() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml{unknown=/tmp}");
        assertNotNull(formatList);
        assertEquals(1, formatList.length());
        assertEquals(new Formatter(XML), formatList.head());
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willFailToParseExtraClosingBrace() {
        resultFormatBuilder.build("xml{unknown=/tmp}}");
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willFailToParseExtraOpeningBrace() {
        resultFormatBuilder.build("xml{{unknown=/tmp}");
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willFailToParseExtraOpeningBraces() {
        resultFormatBuilder.build("xml{{unknown=/tmp}}");
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willFailToParseMissingOpeningBrace() {
        resultFormatBuilder.build("xmlunknown=/tmp}");
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willFailToParseMissingClosingBrace() {
        resultFormatBuilder.build("xml{unknown=/tmp");
    }

    @Specification
    public void willParseSpacesInParameterValues() {
        final List<Formatter> formatList = resultFormatBuilder.build("xml{toDir=/tmp/path\\ with\\ spaces/}");
        assertNotNull(formatList);
        assertEquals(1, formatList.length());
        assertEquals(new Formatter(XML, new File("/tmp/path\\ with\\ spaces/")), formatList.head());
    }
}
