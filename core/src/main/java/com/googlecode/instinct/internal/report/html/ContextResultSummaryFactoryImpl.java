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

import com.googlecode.instinct.internal.util.ParamChecker;
import fj.data.List;
import java.text.MessageFormat;
import javax.xml.xpath.XPathConstants;
import org.apache.tools.ant.types.resources.FileResource;

public final class ContextResultSummaryFactoryImpl implements ContextResultSummaryFactory {
    private static final CamelCaseNameFormatter FORMATTER = new CamelCaseNameFormatter();
    private static final String XPATH_FAILURE_COUNT = "/testsuite/@failures";
    private static final String XPATH_DURATION = "/testsuite/@time";
    private static final String XPATH_COUNT_SPECS = "count(/testsuite/testcase)";
    private static final String XPATH_CONTEXT_NAME = "/testsuite/@name";
    private static final String XPATH_SUBJECT_NAME = "/testsuite/testcase[{0}]/@classname";
    private static final String XPATH_SPEC_NAME = "/testsuite/testcase[{0}]/@name";
    private static final String XPATH_SPEC_FAILURE_TEXT = "/testsuite/testcase[{0}]/failure";
    private XpathEvaluatorFactory xpathEvaluatorFactory = new XpathEvaluatorFactoryImpl();

    public ContextResultSummary createFrom(final FileResource fileResource) throws UnparseableContentException {
        ParamChecker.checkNotNull(fileResource);
        final XpathEvaluator evaluator = xpathEvaluatorFactory.createFor(fileResource);
        final ContextResultSummary summary = new ContextResultSummary();
        summary.setSpecificationCount(evaluator.evaluate(XPATH_COUNT_SPECS, Double.class, XPathConstants.NUMBER).intValue());
        summary.setFailureCount(evaluator.evaluate(XPATH_FAILURE_COUNT, Integer.class));
        summary.setDuration(evaluator.evaluate(XPATH_DURATION, Double.class));
        summary.setContextName(evaluator.evaluate(XPATH_CONTEXT_NAME));
        summary.setFormattedContextName(FORMATTER.convertToSentence(summary.getContextName()));
        List<SpecificationResultSummary> reportedSpecificationResults = List.nil();
        for (Integer i = 1; i <= summary.getSpecificationCount(); i++) {
            final SpecificationResultSummary result = new SpecificationResultSummary();
            result.setSubjectName(evaluator.evaluate(MessageFormat.format(XPATH_SUBJECT_NAME, i)));
            result.setSpecificationName(evaluator.evaluate(MessageFormat.format(XPATH_SPEC_NAME, i)));
            result.setFailureText(evaluator.evaluate(MessageFormat.format(XPATH_SPEC_FAILURE_TEXT, i)));
            reportedSpecificationResults = reportedSpecificationResults.snoc(result);
        }
        summary.setSpecificationResults(reportedSpecificationResults);
        SummaryStatus status = SummaryStatus.passed;
        for (final SpecificationResultSummary result : reportedSpecificationResults.toCollection()) {
            status = SummaryStatus.selectMostNotable(status, result.getStatus());
        }
        summary.setStatus(status);
        return summary;
    }

    void setXpathEvaluatorFactory(final XpathEvaluatorFactory xpathEvaluatorFactory) {
        this.xpathEvaluatorFactory = xpathEvaluatorFactory;
    }
}
