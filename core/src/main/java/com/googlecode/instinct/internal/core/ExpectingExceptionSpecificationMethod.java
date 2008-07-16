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
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.reflect.Method;

public final class ExpectingExceptionSpecificationMethod implements SpecificationMethod {
    private final Method method;

    public ExpectingExceptionSpecificationMethod(final Method method) {
        checkNotNull(method);
        this.method = method;
    }

    public String getName() {
        //        return method.getName();
        throw new UnsupportedOperationException();
    }

    public SpecificationResult run() {
        //        return new SpecificationResultImpl(getName(), new SpecificationRunPendingStatus(), 0L);
        throw new UnsupportedOperationException();
    }
}
