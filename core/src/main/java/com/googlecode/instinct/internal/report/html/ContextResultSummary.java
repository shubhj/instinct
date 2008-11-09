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

import fj.data.List;
import java.util.Collection;

public final class ContextResultSummary {
    private Integer failureCount;
    private Integer specificationCount;
    private Double duration;
    private String contextName;
    private String formattedContextName;
    private List<SpecificationResultSummary> specificationResults;
    private SummaryStatus status = SummaryStatus.passed;

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(final Integer failureCount) {
        this.failureCount = failureCount;
    }

    public Integer getSpecificationCount() {
        return specificationCount;
    }

    public void setSpecificationCount(final Integer specificationCount) {
        this.specificationCount = specificationCount;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(final Double duration) {
        this.duration = duration;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(final String contextName) {
        this.contextName = contextName;
    }

    public String getFormattedContextName() {
        return formattedContextName;
    }

    public void setFormattedContextName(final String formattedContextName) {
        this.formattedContextName = formattedContextName;
    }

    public List<SpecificationResultSummary> getSpecificationResults() {
        return specificationResults;
    }

    public Collection<SpecificationResultSummary> getSpecificationResultsAsCollection() {
        return specificationResults.toCollection();
    }

    public void setSpecificationResults(final List<SpecificationResultSummary> reportedSpecificationResults) {
        specificationResults = reportedSpecificationResults;
    }

    public SummaryStatus getStatus() {
        return status;
    }

    public String getStatusText() {
        return status.name();
    }

    public void setStatus(final SummaryStatus status) {
        this.status = status;
    }
}
