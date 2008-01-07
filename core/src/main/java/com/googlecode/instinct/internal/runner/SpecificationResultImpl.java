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
import com.googlecode.instinct.internal.lang.Primordial;

public final class SpecificationResultImpl extends Primordial implements SpecificationResult {
    private final String specificationName;
    private final SpecificationRunStatus status;
    private final long executionTime;

    public SpecificationResultImpl(final String specificationName, final SpecificationRunStatus status, final long executionTime) {
        checkNotNull(specificationName, status);
        checkNotWhitespace(specificationName);
        this.specificationName = specificationName;
        this.status = status;
        this.executionTime = executionTime;
    }

    public boolean completedSuccessfully() {
        return status.runSuccessful();
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public SpecificationRunStatus getStatus() {
        return status;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
