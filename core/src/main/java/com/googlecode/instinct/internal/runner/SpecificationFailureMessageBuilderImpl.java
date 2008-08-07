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
import static com.googlecode.instinct.internal.util.exception.ExceptionUtil.stackTrace;

public final class SpecificationFailureMessageBuilderImpl implements SpecificationFailureMessageBuilder {
    private static final String NO_FAILURE = "";

    public String buildMessage(final SpecificationRunStatus status) {
        checkNotNull(status);
        return status.runSuccessful() ? NO_FAILURE : buildFailureMessage(status);
    }

    @SuppressWarnings({"TypeMayBeWeakened"})
    private String buildFailureMessage(final SpecificationRunStatus status) {
        final SpecificationFailureException failureException = ((SpecificationRunFailureStatus) status).getDetails();
        return stackTrace(failureException.getCause());
    }
}
