/*
 * Copyright 2006-2007 Tom Adams
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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.internal.util.lang.Primordial;
import java.util.ArrayList;
import java.util.List;

public final class ContextResultImpl extends Primordial implements ContextResult {
    private final List<SpecificationResult> specificationResults = new ArrayList<SpecificationResult>();
    private final String contextName;

    public ContextResultImpl(final String contextName) {
        checkNotWhitespace(contextName);
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }

    public void addSpecificationResult(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        specificationResults.add(specificationResult);
    }

    public List<SpecificationResult> getSpecificationResults() {
        return specificationResults;
    }

    public boolean completedSuccessfully() {
        for (final SpecificationResult specificationResult : specificationResults) {
            if (!specificationResult.completedSuccessfully()) {
                return false;
            }
        }
        return true;
    }

    public int getNumberOfSpecificationsRun() {
        return specificationResults.size();
    }

    public int getNumberOfSuccesses() {
        return countStatus(true);
    }

    public int getNumberOfFailures() {
        return countStatus(false);
    }

    public long getExecutionTime() {
        long executionTime = 0L;
        for (final SpecificationResult specificationResult : specificationResults) {
            executionTime += specificationResult.getExecutionTime();
        }
        return executionTime;
    }

    private int countStatus(final boolean succeeded) {
        int number = 0;
        for (final SpecificationResult specificationResult : specificationResults) {
            if (specificationResult.completedSuccessfully() == succeeded) {
                number++;
            }
        }
        return number;
    }
}
