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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.reflect.ConstructorInvoker;
import com.googlecode.instinct.internal.reflect.ConstructorInvokerImpl;
import static com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus.SPECIFICATION_SUCCESS;
import com.googlecode.instinct.internal.util.AggregatingException;
import com.googlecode.instinct.internal.util.Clock;
import com.googlecode.instinct.internal.util.ClockImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.runner.SpecificationLifecycle;
import com.googlecode.instinct.runner.StandardSpecificationLifecycle;
import fj.F;
import fj.data.Either;
import fj.data.List;
import static fj.data.List.nil;
import fj.data.Option;
import static fj.data.Option.some;
import static fj.data.Option.somes;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

public final class NewSpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final Clock clock = new ClockImpl();

    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        final long startTime = clock.getCurrentTime();
        final SpecificationLifecycle lifecycle = lifecycle(specificationMethod.getContextClass());
        return runLifecycle(startTime, lifecycle, specificationMethod);
    }

    private SpecificationResult runLifecycle(final long startTime, final SpecificationLifecycle lifecycle,
            final SpecificationMethod specificationMethod) {
        List<Option<Throwable>> lifecycleStepErrors = nil();
        final Either<Throwable, ContextClass> createContextResult = lifecycle.createContext(specificationMethod.getContextClass());
        lifecycleStepErrors = lifecycleStepErrors.cons(createContextResult.left().toOption());
        if (createContextResult.isLeft()) {
            return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
        } else {
            final ContextClass contextClass = createContextResult.right().value();
            final Option<Throwable> contextValidationResult = methodValidator.checkContextConstructor(contextClass.getType());
            lifecycleStepErrors = lifecycleStepErrors.cons(contextValidationResult);
            if (contextValidationResult.isSome()) {
                return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
            } else {
                final Object contextInstance = constructorInvoker.invokeNullaryConstructor(contextClass.getType());
                lifecycleStepErrors = lifecycleStepErrors.cons(lifecycle.resetMockery());
                final Either<Throwable, List<Field>> wireResults = lifecycle.wireActors(contextInstance);
                lifecycleStepErrors = lifecycleStepErrors.cons(wireResults.left().toOption());
                if (wireResults.isLeft()) {
                    return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
                } else {
                    final List<LifecycleMethod> befores = contextClass.getBeforeSpecificationMethods();
                    final Option<Throwable> beforeValidationResults = validateMethods(befores);
                    lifecycleStepErrors = lifecycleStepErrors.cons(beforeValidationResults);
                    if (beforeValidationResults.isSome()) {
                        return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
                    } else {
                        final Option<Throwable> beforeResult = lifecycle.runBeforeSpecificationMethods(contextInstance, befores);
                        lifecycleStepErrors = lifecycleStepErrors.cons(beforeResult);
                        if (beforeResult.isSome()) {
                            return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
                        } else {
                            final Option<Throwable> specificationValidationResult = methodValidator.checkMethodHasNoParameters(specificationMethod);
                            lifecycleStepErrors = lifecycleStepErrors.cons(specificationValidationResult);
                            if (specificationValidationResult.isSome()) {
                                return fail(startTime, specificationMethod, somes(lifecycleStepErrors), false);
                            } else {
                                final Option<Throwable> specificationResult = lifecycle.runSpecification(contextInstance, specificationMethod);
                                lifecycleStepErrors = lifecycleStepErrors.cons(specificationResult);
                                lifecycleStepErrors = lifecycleStepErrors
                                        .cons(lifecycle.runAfterSpecificationMethods(contextInstance, contextClass.getAfterSpecificationMethods()));
                                lifecycleStepErrors = lifecycleStepErrors.cons(lifecycle.verifyMocks());
                                return determineResult(startTime, specificationMethod, specificationResult, lifecycleStepErrors);
                            }
                        }
                    }
                }
            }
        }
    }

    private SpecificationResult determineResult(final long startTime, final LifecycleMethod specificationMethod,
            final Option<Throwable> specificationResult, final List<Option<Throwable>> lifecycleStepResults) {
        final List<Throwable> errors = somes(lifecycleStepResults);
        final Option<List<Throwable>> overallResult = errors.isEmpty() ? Option.<List<Throwable>>none() : some(errors);
        if (overallResult.isSome()) {
            return fail(startTime, specificationMethod, overallResult.some(), specificationResult.isSome());
        } else {
            return success(startTime, specificationMethod);
        }
    }

    private Option<Throwable> validateMethods(final List<LifecycleMethod> methods) {
        final List<Option<Throwable>> results = methods.map(new F<LifecycleMethod, Option<Throwable>>() {
            public Option<Throwable> f(final LifecycleMethod method) {
                return methodValidator.checkMethodHasNoParameters(method);
            }
        });
        final List<Throwable> errors = somes(results);
        return errors.isEmpty() ? Option.<Throwable>none() : some((Throwable) new AggregatingException(errors));
    }

    private SpecificationResult success(final long startTime, final LifecycleMethod specificationMethod) {
        return new SpecificationResultImpl(specificationMethod.getName(), SPECIFICATION_SUCCESS, clock.getElapsedTime(startTime));
    }

    private SpecificationResult fail(final long startTime, final LifecycleMethod specificationMethod, final List<Throwable> lifecycleStepErrors,
            final boolean expectingExceptionCandidate) {
        final String message = "The following " + lifecycleStepErrors.length() + " errors ocurred while running the specification";
        final SpecificationFailureException exception =
                new SpecificationFailureException(message, new AggregatingException(lifecycleStepErrors.reverse()));
        final SpecificationRunStatus status = new SpecificationRunFailureStatus(exception, expectingExceptionCandidate);
        return new SpecificationResultImpl(specificationMethod.getName(), status, clock.getElapsedTime(startTime));
    }

    private SpecificationLifecycle lifecycle(final AnnotatedElement contextClass) {
        final Class<? extends SpecificationLifecycle> lifecycleClass =
                contextClass.isAnnotationPresent(Context.class) ? contextClass.getAnnotation(Context.class).lifecycle()
                        : StandardSpecificationLifecycle.class;
        return constructorInvoker.invokeNullaryConstructor(lifecycleClass);
    }
}
