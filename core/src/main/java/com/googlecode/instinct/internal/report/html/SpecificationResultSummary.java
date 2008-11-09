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

public final class SpecificationResultSummary {
    private static final CamelCaseNameFormatter FORMATTER = new CamelCaseNameFormatter();
    private String specificationName;
    private String failureText;
    private String subjectName;
    private SummaryStatus status = SummaryStatus.passed;

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(final String specificationName) {
        this.specificationName = specificationName;
    }

    public String getFormattedSpecificationName() {
        return FORMATTER.convertToSentence(specificationName);
    }

    public String getFailureText() {
        return failureText;
    }

    public void setFailureText(final String failureText) {
        this.failureText = failureText;
        if (failureText != null) {
            status = SummaryStatus.failed;
        }
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(final String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStatusText() {
        return status.name();
    }

    public SummaryStatus getStatus() {
        return status;
    }
}
