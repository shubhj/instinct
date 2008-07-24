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

package com.googlecode.instinct.internal.util;

import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ClassInstantiator;
import com.googlecode.instinct.internal.util.instance.ClassInstantiatorImpl;

@Suggest({"Wrap 'unexpected ...' jMock cardinality exception if you a mock is used in a test without expectations being set on it"})
public final class ExceptionSanitiserImpl implements ExceptionSanitiser {
    private final ClassInstantiator instantiator = new ClassInstantiatorImpl();

    public Throwable sanitise(final Throwable throwable) {
        checkNotNull(throwable);
        try {
            if (instantiator.instantiateClass("org.jmock.api.ExpectationError").isAssignableFrom(throwable.getClass())) {
                return sanitiseJMockExpectationError(throwable);
            } else {
                return throwable;
            }
        } catch (Exception e) {
            return throwable;
        }
    }

    private Throwable sanitiseJMockExpectationError(final Throwable throwable) {
        final String message = "Unexpected invocation. You may need to wrap the code in your new Expections(){{}} block with cardinality " +
                "constraints, one(), atLeast(), etc.\n";
        return new SpecificationFailureException(message + throwable.toString(), throwable);
    }
}
