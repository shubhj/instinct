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

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.internal.runner.ErrorLocation;
import static com.googlecode.instinct.internal.runner.ErrorLocation.SPECIFICATION;
import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.Effect;
import fj.data.List;
import static fj.data.List.nil;
import java.lang.reflect.Method;

/** A specification that expects an exception to be thrown. The specification will fail if the expected exception is not thrown. */
public final class ExpectingExceptionSpecificationMethod extends Primordial implements SpecificationMethod {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final Clock clock = new ClockImpl();
    private final Class<?> contextType;
    private final Method method;
    private final List<LifecycleMethod> beforeSpecificationMethods;
    private final List<LifecycleMethod> afterSpecificationMethods;
    private List<SpecificationListener> specificationListeners = nil();

    public <T> ExpectingExceptionSpecificationMethod(final Class<T> contextType, final Method method,
            final List<LifecycleMethod> beforeSpecificationMethods, final List<LifecycleMethod> afterSpecificationMethods) {
        checkNotNull(contextType, method, beforeSpecificationMethods, afterSpecificationMethods);
        this.contextType = contextType;
        this.method = method;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
    }

    @SuppressWarnings({"unchecked"})
    public <T extends Throwable> Class<T> getExpectedException() {
        return (Class<T>) method.getAnnotation(Specification.class).expectedException();
    }

    public String getExpectedExceptionMessage() {
        return method.getAnnotation(Specification.class).withMessage();
    }

    public List<LifecycleMethod> getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public List<LifecycleMethod> getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getContextClass() {
        return (Class<T>) contextType;
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners = specificationListeners.cons(specificationListener);
    }

    public SpecificationResult run() {
        notifyListenersOfPreSpecification(this);
        final SpecificationResult result = doRun();
        notifyListenersOfPostSpecification(this, result);
        return result;
    }

    @Override
    public String toString() {
        return ExpectingExceptionSpecificationMethod.class.getSimpleName() + "[method=" + method + ";before=" +
                beforeSpecificationMethods.toCollection() + ";after=" + afterSpecificationMethods.toCollection() + "]";
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    private SpecificationResult doRun() {
        final Class<? extends Throwable> expectedException = method.getAnnotation(Specification.class).expectedException();
        final long startTime = clock.getCurrentTime();
        final SpecificationResult result = specificationRunner.run(this);
        if (result.completedSuccessfully()) {
            final String message = "Expected exception " + expectedException.getName() + " was not thrown in body of specification";
            return fail(startTime, message, SPECIFICATION);
        } else {
            final SpecificationRunFailureStatus failureStatus = (SpecificationRunFailureStatus) result.getStatus();
            final Throwable exceptionThrown = failureStatus.getDetails();
            final ErrorLocation location = failureStatus.getErrorLocation();
            return processExpectedFailure(startTime, expectedException, exceptionFinder.getRootCause(exceptionThrown), location);
        }
    }

    @SuppressWarnings({"TypeMayBeWeakened"})
    private <T extends Throwable> SpecificationResult processExpectedFailure(final long startTime,
            final Class<? extends Throwable> expectedExceptionClass, final Throwable thrownException, final ErrorLocation location) {
        if (location == SPECIFICATION) {
            if (thrownException.getClass().equals(expectedExceptionClass)) {
                return checkExpectedMessage(startTime, thrownException);
            } else {
                final String message =
                        "Expected exception was not thrown in body of specification\nExpected: " + expectedExceptionClass + "\n     got: " +
                                thrownException.getClass();
                return fail(startTime, message, thrownException, location);
            }
        } else {
            final String message = "An unexpected error was thrown while running " + location.getMessage().toLowerCase();
            return fail(startTime, message, thrownException, location);
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
            return fail(startTime, message, SPECIFICATION);
        }
    }

    private void notifyListenersOfPreSpecification(final SpecificationMethod specificationMethod) {
        specificationListeners.foreach(new Effect<SpecificationListener>() {
            public void e(final SpecificationListener listener) {
                listener.preSpecificationMethod(specificationMethod);
            }
        });
    }

    private void notifyListenersOfPostSpecification(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        specificationListeners.foreach(new Effect<SpecificationListener>() {
            public void e(final SpecificationListener listener) {
                listener.postSpecificationMethod(specificationMethod, specificationResult);
            }
        });
    }

    private SpecificationResult fail(final long startTime, final String message, final Throwable thrownException, final ErrorLocation location) {
        return fail(startTime, new SpecificationFailureException(message, thrownException), location);
    }

    private SpecificationResult fail(final long startTime, final String message, final ErrorLocation location) {
        return fail(startTime, new SpecificationFailureException(message), location);
    }

    private SpecificationResult fail(final long startTime, final SpecificationFailureException failure, final ErrorLocation location) {
        return new SpecificationResultImpl(getName(), new SpecificationRunFailureStatus(failure, location), clock.getElapsedTime(startTime));
    }
}
