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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.text.DecimalFormat;
import java.text.Format;
import org.apache.tools.ant.BuildException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

public final class ReportWriterImpl implements ReportWriter {
    public static final String INSTINCT_SPEC_REPORT_TEMPLATE = "instinct_spec_report.vm";
    private static final Format DECIMAL_FORMATTER = new DecimalFormat("0.0##");
    private ReportTemplate template = new VelocityReportTemplate(INSTINCT_SPEC_REPORT_TEMPLATE);

    @SuppressWarnings({"OverlyBroadCatchBlock"})
    public String write(final SpecificationReport report) {
        checkNotNull(report);
        final Context context = new VelocityContext();
        final String durationFormatted = DECIMAL_FORMATTER.format(report.getDuration());
        context.put("duration", durationFormatted);
        context.put("durationLabel", "1.0".equals(durationFormatted) ? "second" : "seconds");
        context.put("specificationCount", report.getSpecificationCount());
        context.put("specificationLabel", report.getSpecificationCount() == 1 ? "specification" : "specifications");
        context.put("failureCount", report.getFailureCount());
        context.put("failureLabel", report.getFailureCount() == 1 ? "failure" : "failures");
        context.put("summaries", report.getSummaries().toCollection());
        context.put("status", report.getStatusText());
        try {
            return template.mergeWith(context);
        } catch (Exception e) {
            throw new BuildException("Could not export results to template", e);
        }
    }

    void setTemplate(final ReportTemplate template) {
        this.template = template;
    }
}
