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

package com.googlecode.instinct.internal.report.html;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import fj.data.List;
import javax.xml.xpath.XPathConstants;
import org.apache.tools.ant.types.resources.FileResource;
import org.jmock.Expectations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AContextResultSummaryFactoryImpl {
    private static final String XPATH_FAILURE_COUNT = "/testsuite/@failures";
    private static final String XPATH_DURATION = "/testsuite/@time";
    private static final String XPATH_COUNT_SPECS = "count(/testsuite/testcase)";
    private static final String XPATH_CONTEXT_NAME = "/testsuite/@name";
    private static final String XPATH_SUBJECT_1_NAME = "/testsuite/testcase[1]/@classname";
    private static final String XPATH_SPEC_1_NAME = "/testsuite/testcase[1]/@name";
    private static final String XPATH_SPEC_1_FAILURE_TEXT = "/testsuite/testcase[1]/failure";
    private static final String XPATH_SUBJECT_2_NAME = "/testsuite/testcase[2]/@classname";
    private static final String XPATH_SPEC_2_NAME = "/testsuite/testcase[2]/@name";
    private static final String XPATH_SPEC_2_FAILURE_TEXT = "/testsuite/testcase[2]/failure";
    private static final String XPATH_SUBJECT_3_NAME = "/testsuite/testcase[3]/@classname";
    private static final String XPATH_SPEC_3_NAME = "/testsuite/testcase[3]/@name";
    private static final String XPATH_SPEC_3_FAILURE_TEXT = "/testsuite/testcase[3]/failure";

    @Subject private ContextResultSummaryFactoryImpl factory;
    @Mock private XpathEvaluatorFactory xpathEvaluatorFactory;
    @Mock private XpathEvaluator xpathEvaluator;
    @Mock private FileResource fileResource;

    @BeforeSpecification
    public void createSubject() {
        factory = new ContextResultSummaryFactoryImpl();
        factory.setXpathEvaluatorFactory(xpathEvaluatorFactory);
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 1 should not be null")
    public void willNotAllowNullToBeProvidedAsAFileResource() throws Exception {
        factory.createFrom(null);
    }

    @Specification(expectedException = UnparseableContentException.class)
    public void willPropogateUnparsableContentExceptionIfTheFileResourceIsUnparsable() throws Exception {
        expect.that(new Expectations() {
            {
                one(xpathEvaluatorFactory).createFor(fileResource);
                will(throwException(new UnparseableContentException("")));
            }
        });
        factory.createFrom(fileResource);
    }

    @Specification
    public void willConstructAContextResultSummaryUsingTheXpathEvaluator() throws Exception {
        expect.that(new Expectations() {
            {
                one(xpathEvaluatorFactory).createFor(fileResource);
                will(returnValue(xpathEvaluator));
                one(xpathEvaluator).evaluate(XPATH_COUNT_SPECS, Double.class, XPathConstants.NUMBER);
                will(returnValue(3.0));
                one(xpathEvaluator).evaluate(XPATH_FAILURE_COUNT, Integer.class);
                will(returnValue(1));
                one(xpathEvaluator).evaluate(XPATH_DURATION, Double.class);
                will(returnValue(5.5));
                one(xpathEvaluator).evaluate(XPATH_CONTEXT_NAME);
                will(returnValue("package.TheContextName"));
                one(xpathEvaluator).evaluate(XPATH_SUBJECT_1_NAME);
                will(returnValue("package.TheContextName"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_1_NAME);
                will(returnValue("specifiesTheFirstThing"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_1_FAILURE_TEXT);
                will(returnValue(null));
                one(xpathEvaluator).evaluate(XPATH_SUBJECT_2_NAME);
                will(returnValue("package.TheContextName"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_2_NAME);
                will(returnValue("specifiesTheSecondThing"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_2_FAILURE_TEXT);
                will(returnValue(null));
                one(xpathEvaluator).evaluate(XPATH_SUBJECT_3_NAME);
                will(returnValue("package.TheContextName"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_3_NAME);
                will(returnValue("specifiesTheThirdThing"));
                one(xpathEvaluator).evaluate(XPATH_SPEC_3_FAILURE_TEXT);
                will(returnValue("It failed!"));
            }
        });
        final ContextResultSummary summary = factory.createFrom(fileResource);
        assertEquals("package.TheContextName", summary.getContextName());
        assertEquals("The context name", summary.getFormattedContextName());
        assertEquals(new Double(5.5), summary.getDuration());
        assertEquals(new Integer(1), summary.getFailureCount());
        assertEquals(new Integer(3), summary.getSpecificationCount());
        assertEquals(SummaryStatus.failed, summary.getStatus());
        assertEquals("failed", summary.getStatusText());
        final List<SpecificationResultSummary> results = summary.getSpecificationResults();
        assertNull(results.index(0).getFailureText());
        assertEquals("specifiesTheFirstThing", results.index(0).getSpecificationName());
        assertEquals("specifies the first thing", results.index(0).getFormattedSpecificationName());
        assertEquals(SummaryStatus.passed, results.index(0).getStatus());
        assertEquals("passed", results.index(0).getStatusText());
        assertEquals("package.TheContextName", results.index(0).getSubjectName());
        assertNull(results.index(1).getFailureText());
        assertEquals("specifiesTheSecondThing", results.index(1).getSpecificationName());
        assertEquals("specifies the second thing", results.index(1).getFormattedSpecificationName());
        assertEquals(SummaryStatus.passed, results.index(1).getStatus());
        assertEquals("passed", results.index(1).getStatusText());
        assertEquals("package.TheContextName", results.index(1).getSubjectName());
        assertEquals("It failed!", results.index(2).getFailureText());
        assertEquals("specifiesTheThirdThing", results.index(2).getSpecificationName());
        assertEquals("specifies the third thing", results.index(2).getFormattedSpecificationName());
        assertEquals(SummaryStatus.failed, results.index(2).getStatus());
        assertEquals("failed", results.index(2).getStatusText());
        assertEquals("package.TheContextName", results.index(2).getSubjectName());
    }
}
