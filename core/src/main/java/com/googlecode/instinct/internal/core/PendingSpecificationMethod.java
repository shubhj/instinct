/*
* Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunPendingStatus;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.marker.annotate.Specification.NO_REASON;
import java.lang.reflect.Method;

public final class PendingSpecificationMethod implements SpecificationMethod {
    private Method method;

    public PendingSpecificationMethod(final Method method) {
        checkNotNull(method);
        this.method = method;
    }

    public String getPendingReason() {
        return NO_REASON;
    }

    public SpecificationResult run() {
        return new SpecificationResultImpl(getName(), new SpecificationRunPendingStatus(), 0L);
    }

    public String getName() {
        return method.getName();
    }
}
