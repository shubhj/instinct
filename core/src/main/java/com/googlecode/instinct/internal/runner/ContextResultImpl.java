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

import com.googlecode.instinct.internal.lang.Primordial;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.internal.core.ContextClass;
import fj.F;
import fj.data.List;
import static fj.data.List.nil;

public final class ContextResultImpl extends Primordial implements ContextResult {
    private final ContextClass contextClass;
    private List<SpecificationResult> specificationResults = nil();

    public ContextResultImpl(final ContextClass contextClass) {
        checkNotNull(contextClass);
        checkNotWhitespace(contextClass.getName());
        this.contextClass = contextClass;
    }

    public void addSpecificationResult(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        specificationResults = specificationResults.cons(specificationResult);
    }

    public List<SpecificationResult> getSpecificationResults() {
        return specificationResults.reverse();
    }

    public ContextClass getContextClass() {
        return contextClass;
    }

    public boolean completedSuccessfully() {
        return specificationResults.forall(new F<SpecificationResult, Boolean>() {
            public Boolean f(final SpecificationResult result) {
                return result.completedSuccessfully();
            }
        });
    }

    public int getNumberOfSpecificationsRun() {
        return specificationResults.length();
    }

    public int getNumberOfSuccesses() {
        return countStatus(true);
    }

    public int getNumberOfFailures() {
        return countStatus(false);
    }

    public long getExecutionTime() {
        return specificationResults.foldLeft(new F<Long, F<SpecificationResult, Long>>() {
            public F<SpecificationResult, Long> f(final Long time) {
                return new F<SpecificationResult, Long>() {
                    public Long f(final SpecificationResult result) {
                        return time + result.getExecutionTime();
                    }
                };
            }
        }, 0L);
    }

    private int countStatus(final boolean success) {
        return specificationResults.find(new F<SpecificationResult, Boolean>() {
            public Boolean f(final SpecificationResult result) {
                return result.completedSuccessfully() == success;
            }
        }).length();
    }
}
