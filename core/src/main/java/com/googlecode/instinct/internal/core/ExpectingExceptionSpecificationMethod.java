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

import com.googlecode.instinct.internal.runner.CompleteSpecificationRunner;
import com.googlecode.instinct.internal.runner.CompleteSpecificationRunnerImpl;
import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Specification;
import java.lang.reflect.Method;

public final class ExpectingExceptionSpecificationMethod implements SpecificationMethod {
    private final CompleteSpecificationRunner specificationRunner = new CompleteSpecificationRunnerImpl();
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final Clock clock = new ClockImpl();
    private final Method method;

    public ExpectingExceptionSpecificationMethod(final Method method) {
        checkNotNull(method);
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    @SuppressWarnings({"unchecked"})
    public <T extends Throwable> Class<T> getExpectedException() {
        return (Class<T>) method.getAnnotation(Specification.class).expectedException();
    }

    public String getExpectedExceptionMessage() {
        return method.getAnnotation(Specification.class).withMessage();
    }

    // SUPPRESS IllegalCatch {
    public SpecificationResult run() {
        final Class<? extends Throwable> expectedException = method.getAnnotation(Specification.class).expectedException();
        final long startTime = clock.getCurrentTime();
        try {
            specificationRunner.run(this);
            return fail(startTime, "Expected exception " + expectedException.getName() + " was not thrown in body of specification");
        } catch (Throwable throwable) {
            return processExpectedFailure(startTime, expectedException, exceptionFinder.getRootCause(throwable));
        }
    }

    // } SUPPRESS IllegalCatch

    @SuppressWarnings({"TypeMayBeWeakened"})
    private <T extends Throwable> SpecificationResult processExpectedFailure(final long startTime, final Class<T> expectedExceptionClass,
            final Throwable thrownException) {
        if (thrownException.getClass().equals(expectedExceptionClass)) {
            return checkExpectedMessage(startTime, thrownException);
        } else {
            final String message =
                    "Expected exception was not thrown\nExpected: " + expectedExceptionClass + "\n     got: " + thrownException.getClass();
            final Throwable failure = new SpecificationFailureException(message, thrownException);
            final SpecificationRunStatus status = new SpecificationRunFailureStatus(failure);
            return new SpecificationResultImpl(getName(), status, clock.getElapsedTime(startTime));
        }
    }

    private SpecificationResult checkExpectedMessage(final long startTime, final Throwable exceptionThrown) {
        if (getExpectedExceptionMessage().equals(Specification.NO_MESSAGE)) {
            return new SpecificationResultImpl(getName(), SPECIFICATION_SUCCESS, clock.getElapsedTime(startTime));
        } else if (getExpectedExceptionMessage().equals(exceptionThrown.getMessage())) {
            return new SpecificationResultImpl(getName(), SPECIFICATION_SUCCESS, clock.getElapsedTime(startTime));
        } else {
            final String message = "Expected exception message was incorrect\nExpected: " + getExpectedExceptionMessage() + "\n     got: " +
                    exceptionThrown.getMessage();
            return fail(startTime, message);
        }
    }

    private SpecificationResult fail(final long startTime, final String message) {
        final SpecificationRunStatus status = new SpecificationRunFailureStatus(new AssertionError(message));
        return new SpecificationResultImpl(getName(), status, clock.getElapsedTime(startTime));
    }
}
