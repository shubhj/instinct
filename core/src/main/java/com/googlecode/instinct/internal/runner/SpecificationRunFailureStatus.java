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

public final class SpecificationRunFailureStatus extends Primordial implements SpecificationRunStatus {
    private final Throwable error;
    private final boolean expectedExceptionCandidate;

    public SpecificationRunFailureStatus(final Throwable error, final boolean expectedExceptionCandidate) {
        this.expectedExceptionCandidate = expectedExceptionCandidate;
        checkNotNull(error, expectedExceptionCandidate);
        this.error = error;
    }

    public Throwable getDetails() {
        return error;
    }

    public boolean isExpectedExceptionCandidate() {
        return expectedExceptionCandidate;
    }

    public boolean runSuccessful() {
        return false;
    }
}
