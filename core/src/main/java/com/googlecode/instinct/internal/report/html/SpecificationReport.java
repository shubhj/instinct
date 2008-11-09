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

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class SpecificationReport {
    private List<ContextResultSummary> summaries = List.nil();
    private Double duration = 0.0;
    private Integer specificationCount = 0;
    private Integer failureCount = 0;
    private SummaryStatus status = SummaryStatus.passed;

    public void add(final ContextResultSummary summary) {
        summaries = summaries.snoc(summary);
        duration += summary.getDuration();
        specificationCount += summary.getSpecificationCount();
        failureCount += summary.getFailureCount();
        status = SummaryStatus.selectMostNotable(status, summary.getStatus());
    }

    public List<ContextResultSummary> getSummaries() {
        return summaries;
    }

    public Double getDuration() {
        return duration;
    }

    public Integer getSpecificationCount() {
        return specificationCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public String getStatusText() {
        return status.name();
    }
}
