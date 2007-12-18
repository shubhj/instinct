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

import com.googlecode.instinct.actor.ActorAutoWirer;
import com.googlecode.instinct.actor.ActorAutoWirerImpl;
import com.googlecode.instinct.expect.behaviour.Mocker;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.lang.ConstructorInvoker;
import com.googlecode.instinct.internal.util.lang.ConstructorInvokerImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.runner.SpecificationListener;
import java.util.ArrayList;
import java.util.Collection;
import org.jmock.api.ExpectationError;

// SUPPRESS GenericIllegalRegex|MethodLength {
@Fix("This class is huge. Split it!")
@SuppressWarnings({"OverlyCoupledClass"})
public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final Collection<SpecificationListener> specificationListeners = new ArrayList<SpecificationListener>();
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();
    private final Clock clock = new ClockImpl();
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final MethodInvoker methodInvoker = new MethodInvokerImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    //    private final MockVerifier mockVerifier = new MockVerifierImpl();
    private final MethodInvokerFactory methodInvokerFactory = new MethodInvokerFactoryImpl();

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners.add(specificationListener);
    }

    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        notifyListenersOfPreSpecification(specificationMethod);
        final SpecificationResult specificationResult = doRun(specificationMethod);
        notifyListenersOfPostSpecification(specificationMethod, specificationResult);
        return specificationResult;
    }

    @Suggest({"Make a clock wrapper that looks like org.jbehave.core.util.Timer."})
    private SpecificationResult doRun(final SpecificationMethod specificationMethod) {
        final long startTime = clock.getCurrentTime();
        if (specificationMethod.isPending()) {
            return createSpecResult(specificationMethod, new SpecificationRunPendingStatus(), startTime);
        } else {
            return doNonPendingRun(specificationMethod, startTime);
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    private SpecificationResult doNonPendingRun(final SpecificationMethod specificationMethod, final long startTime) {
        final Class<? extends Throwable> expectedException = specificationMethod.getExpectedException();
        try {
            // expose the context class, rather than getting the method
            final Class<?> contextClass = specificationMethod.getSpecificationMethod().getContextClass();
            final Object instance = invokeConstructor(contextClass);
            runSpecificationLifecycle(instance, specificationMethod);
            if (expectedException.equals(Specification.NoExpectedException.class)) {
                return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
            } else {
                final String message = "Expected exception " + expectedException + " was not thrown in body of specification";
                final Throwable failure = new AssertionError(message);
                final SpecificationRunStatus status = new SpecificationRunFailureStatus(failure);
                return createSpecResult(specificationMethod, status, startTime);
            }
        } catch (Throwable exceptionThrown) {
            if (expectedException.equals(Specification.NoExpectedException.class)) {
                final SpecificationRunStatus status = new SpecificationRunFailureStatus(wrapCommonExceptions(exceptionThrown));
                return createSpecResult(specificationMethod, status, startTime);
            } else {
                final Throwable exceptionThrownBySpec = exceptionFinder.getRootCause(exceptionThrown);
                return processExpectedFailure(specificationMethod, startTime, expectedException, exceptionThrownBySpec);
            }
        }
    }

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private <T extends Throwable> SpecificationResult processExpectedFailure(final SpecificationMethod specificationMethod, final long startTime,
            final Class<T> expectedExceptionClass, final Throwable thrownException) {
        if (thrownException.getClass().equals(expectedExceptionClass)) {
            final String expectedMessage = specificationMethod.getExpectedExceptionMessage();
            if (expectedMessage.equals(Specification.NO_MESSAGE)) {
                return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
            } else {
                if (expectedMessage.equals(thrownException.getMessage())) {
                    return createSpecResult(specificationMethod, SPECIFICATION_SUCCESS, startTime);
                } else {
                    final String message =
                            "Expected exception message was incorrect\nExpected: " + expectedMessage + "\n     got: " + thrownException.getMessage();
                    final Throwable failure = new AssertionError(message);
                    final SpecificationRunStatus status = new SpecificationRunFailureStatus(failure);
                    return createSpecResult(specificationMethod, status, startTime);
                }
            }
        } else {
            final String message =
                    "Expected exception was not thrown\nExpected: " + expectedExceptionClass + "\n     got: " + thrownException.getClass();
            final Throwable failure = new SpecificationFailureException(message, thrownException);
            final SpecificationRunStatus status = new SpecificationRunFailureStatus(failure);
            return createSpecResult(specificationMethod, status, startTime);
        }
    }

    @Fix({"Test the wrapping of jMock exceptions here."})
    @Suggest(
            {"Wrap 'unexpected ...' jMock cardinality exception if you a mock is used in a test without expectations being set on it", "Call this thing an ExceptionSanitiser", "May help fix Paul's bug: defect-11"})
    private Throwable wrapCommonExceptions(final Throwable throwable) {
        if (throwable instanceof ExpectationError) {
            final String message = "Unexpected invocation. You may need to wrap the code in your new Expections(){{}} block with cardinality " +
                    "constraints, one(), atLeast(), etc.\n";
            return new SpecificationFailureException(message + throwable.toString(), throwable);
        }
        return throwable;
    }

    private SpecificationResult createSpecResult(final SpecificationMethod specificationMethod, final SpecificationRunStatus runStatus,
            final long startTime) {
        final long executionTime = clock.getElapsedTime(startTime);
        return new SpecificationResultImpl(specificationMethod.getName(), runStatus, executionTime);
    }

    @Suggest({"How do we expose this lifecycle?"})
    private void runSpecificationLifecycle(final Object contextInstance, final SpecificationMethod specificationMethod) {
        Mocker.reset();
        actorAutoWirer.autoWireFields(contextInstance);
        try {
            runMethods(contextInstance, specificationMethod.getBeforeSpecificationMethods());
            runSpecificationMethod(contextInstance, specificationMethod.getSpecificationMethod());
        } finally {
            try {
                runMethods(contextInstance, specificationMethod.getAfterSpecificationMethods());
            } finally {
                // Note. We need to make sure we capture and report all errors correctly.
                Mocker.verify();
            }
        }
    }

    private void runSpecificationMethod(final Object contextInstance, final LifecycleMethod specificationMethod) {
        methodValidator.checkMethodHasNoParameters(specificationMethod);
        final MethodInvoker specMethodInvoker = methodInvokerFactory.create(specificationMethod);
        specMethodInvoker.invokeMethod(contextInstance, specificationMethod.getMethod());
    }

    private void notifyListenersOfPreSpecification(final SpecificationMethod specificationMethod) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.preSpecificationMethod(specificationMethod);
        }
    }

    private void notifyListenersOfPostSpecification(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationListener.postSpecificationMethod(specificationMethod, specificationResult);
        }
    }

    private void runMethods(final Object instance, final Iterable<LifecycleMethod> methods) {
        for (final LifecycleMethod method : methods) {
            runMethod(instance, method);
        }
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private void runMethod(final Object instance, final LifecycleMethod method) {
        methodValidator.checkMethodHasNoParameters(method);
        methodInvoker.invokeMethod(instance, method.getMethod());
    }

    @Suggest("Share this check logic with the IntelliJ integration.")
    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }
}
// } SUPPRESS GenericIllegalRegex|MethodLength
