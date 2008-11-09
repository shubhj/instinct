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
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import fj.Effect;
import fj.data.List;
import org.apache.velocity.context.Context;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AReportWriterImpl {
    @Subject private ReportWriterImpl writer;
    private TestReportTemplate template = new TestReportTemplate();

    @BeforeSpecification
    public void createSubject() {
        writer = new ReportWriterImpl();
        writer.setTemplate(template);
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 1 should not be null")
    public void willNotAcceptANullReport() {
        writer.write(null);
    }

    @Specification
    public void willEstablishRequiredValuesInContext() {
        final ContextResultSummary resultSummary = new ContextResultSummary();
        resultSummary.setContextName("the.ContextName");
        resultSummary.setDuration(7.6);
        resultSummary.setFailureCount(0);
        resultSummary.setFormattedContextName("Context Name");
        resultSummary.setSpecificationCount(99);
        resultSummary.setSpecificationResults(List.<SpecificationResultSummary>nil());
        resultSummary.setStatus(SummaryStatus.passed);
        final SpecificationReport report = new SpecificationReport();
        report.add(resultSummary);
        expect.that(writer.write(report)).isEqualTo("TestReportTemplate canned response");
        final List<String> expectedKeys = List.list("duration", "durationLabel", "specificationCount", "specificationLabel", "failureCount",
                "failureLabel", "summaries", "status");
        expect.that(template.getContext().getKeys()).isOfSize(expectedKeys.length());
        expectedKeys.foreach(new Effect<String>() {
            public void e(final String s) {
                expect.that(template.getContext().get(s)).isNotNull();
            }
        });
    }

    @Specification
    public void willConstructLabelsOfCorrectPluralityWhenSingular() {
        final ContextResultSummary resultSummary = new ContextResultSummary();
        resultSummary.setContextName("the.ContextName");
        resultSummary.setDuration(1.0);
        resultSummary.setFailureCount(1);
        resultSummary.setFormattedContextName("Context Name");
        resultSummary.setSpecificationCount(1);
        resultSummary.setSpecificationResults(List.<SpecificationResultSummary>nil());
        resultSummary.setStatus(SummaryStatus.failed);
        final SpecificationReport report = new SpecificationReport();
        report.add(resultSummary);
        expect.that(writer.write(report)).isEqualTo("TestReportTemplate canned response");
        expect.that(template.getContext().get("durationLabel")).isEqualTo("second");
        expect.that(template.getContext().get("specificationLabel")).isEqualTo("specification");
        expect.that(template.getContext().get("failureLabel")).isEqualTo("failure");
    }

    @Specification
    public void willConstructLabelsOfCorrectPluralityWhenNotSingular() {
        final ContextResultSummary resultSummary = new ContextResultSummary();
        resultSummary.setContextName("the.ContextName");
        resultSummary.setDuration(0.9);
        resultSummary.setFailureCount(0);
        resultSummary.setFormattedContextName("Context Name");
        resultSummary.setSpecificationCount(0);
        resultSummary.setSpecificationResults(List.<SpecificationResultSummary>nil());
        resultSummary.setStatus(SummaryStatus.pending);
        final SpecificationReport report = new SpecificationReport();
        report.add(resultSummary);
        expect.that(writer.write(report)).isEqualTo("TestReportTemplate canned response");
        expect.that(template.getContext().get("durationLabel")).isEqualTo("seconds");
        expect.that(template.getContext().get("specificationLabel")).isEqualTo("specifications");
        expect.that(template.getContext().get("failureLabel")).isEqualTo("failures");
    }

    private class TestReportTemplate implements ReportTemplate {
        private Context context;

        public String mergeWith(final Context context) throws Exception {
            this.context = context;
            return "TestReportTemplate canned response";
        }

        public Context getContext() {
            return context;
        }
    }
}
