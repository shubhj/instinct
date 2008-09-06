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
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.exception.ExceptionFinder;
import com.googlecode.instinct.internal.util.exception.ExceptionFinderImpl;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiser;
import com.googlecode.instinct.internal.util.exception.ExceptionSanitiserImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.runner.SpecificationLifecycle;
import com.googlecode.instinct.runner.StandardSpecificationLifecycle;
import fj.F;
import fj.Unit;
import static fj.Unit.unit;
import fj.data.Either;
import fj.data.List;
import fj.data.NonEmptyList;
import fj.data.Option;
import static fj.data.Option.some;
import static fj.data.Option.somes;
import fj.data.Validation;
import static fj.data.Validation.validation;
import fj.pre.Semigroup;
import java.lang.reflect.AnnotatedElement;

public final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private final LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final ExceptionSanitiser exceptionSanitiser = new ExceptionSanitiserImpl();
    private final Clock clock = new ClockImpl();

    public SpecificationResult run(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        final long startTime = clock.getCurrentTime();
        final SpecificationLifecycle lifecycle = lifecycle(specificationMethod.getContextClass());
        return runLifecycle(startTime, lifecycle, specificationMethod);
    }

    private SpecificationResult runLifecycle(final long startTime, final SpecificationLifecycle lifecycle,
            final SpecificationMethod specificationMethod) {
        final Either<Throwable, ContextClass> createContext = lifecycle.createContext().f(specificationMethod.getContextClass());
        if (createContext.isLeft()) {
            return fail(startTime, specificationMethod, createContext.left().value(), Option.<Throwable>none());
        } else {
            final ContextClass contextClass = createContext.right().value();
            final Validation<Throwable, Unit> validation = validateSpecification(contextClass, specificationMethod);
            if (validation.isFail()) {
                return fail(startTime, specificationMethod, validation.fail(), Option.<Throwable>none());
            } else {
                return runSpecification(startTime, lifecycle, contextClass, specificationMethod);
            }
        }
    }

    private SpecificationResult runSpecification(final long startTime, final SpecificationLifecycle lifecycle, final ContextClass contextClass,
            final SpecificationMethod specificationMethod) {
        final Object contextInstance = constructorInvoker.invokeNullaryConstructor(contextClass.getType());
        final Validation<Throwable, Unit> preSpecificationSteps =
                validate(lifecycle.resetMockery()._1()).sequence(validation(rightToUnit(lifecycle.wireActors().f(contextInstance)))).sequence(
                        validate(lifecycle.runBeforeSpecificationMethods().f(contextInstance, contextClass.getBeforeSpecificationMethods())));
        if (preSpecificationSteps.isFail()) {
            return fail(startTime, specificationMethod, preSpecificationSteps.fail(), Option.<Throwable>none());
        } else {
            final Option<Throwable> specification = lifecycle.runSpecification().f(contextInstance, specificationMethod);
            final Option<NonEmptyList<Throwable>> result = preSpecificationSteps.nel().accumulate(throwables(), validate(specification).nel(),
                    validate(lifecycle.runAfterSpecificationMethods().f(contextInstance, contextClass.getAfterSpecificationMethods())).nel(),
                    validate(lifecycle.verifyMocks()._1()).nel());
            if (result.isSome()) {
                return fail(startTime, specificationMethod, result.some().toList(), specification);
            } else {
                return success(startTime, specificationMethod);
            }
        }
    }

    @Suggest({"Push validation into the lifecycle"})
    private Validation<Throwable, Unit> validateSpecification(final ContextClass contextClass, final LifecycleMethod specificationMethod) {
        final Validation<Throwable, Unit> contextValidation = validate(methodValidator.checkContextConstructor(contextClass.getType()));
        final Validation<Throwable, Unit> beforesValidation = validate(validateMethods(contextClass.getBeforeSpecificationMethods()));
        final Validation<Throwable, Unit> specValidation = validate(methodValidator.checkMethodHasNoParameters(specificationMethod));
        final Validation<Throwable, Unit> aftersValidation = validate(validateMethods(contextClass.getAfterSpecificationMethods()));
        return contextValidation.sequence(beforesValidation).sequence(specValidation).sequence(aftersValidation);
    }

    private Option<Throwable> validateMethods(final List<LifecycleMethod> methods) {
        final List<Option<Throwable>> results = methods.map(new F<LifecycleMethod, Option<Throwable>>() {
            public Option<Throwable> f(final LifecycleMethod method) {
                return methodValidator.checkMethodHasNoParameters(method);
            }
        });
        final List<Throwable> errors = somes(results);
        final String message = "At least one specification lifecycle method failed validation";
        return errors.isEmpty() ? Option.<Throwable>none() : some((Throwable) new AggregatingException(message, errors));
    }

    private SpecificationResult success(final long startTime, final SpecificationMethod specificationMethod) {
        return new SpecificationResultImpl(specificationMethod, SPECIFICATION_SUCCESS, clock.getElapsedTime(startTime));
    }

    private SpecificationResult fail(final long startTime, final SpecificationMethod specificationMethod, final Throwable error,
            final Option<Throwable> specificationError) {
        return fail(startTime, specificationMethod, List.<Throwable>nil().cons(error), specificationError);
    }

    private SpecificationResult fail(final long startTime, final SpecificationMethod specificationMethod, final List<Throwable> lifecycleErrors,
            final Option<Throwable> specificationError) {
        final List<Throwable> realErrors = realErrors(lifecycleErrors.reverse());
        final Option<Throwable> realSpecificationError = specificationError.map(new F<Throwable, Throwable>() {
            public Throwable f(final Throwable throwable) {
                return realError(throwable);
            }
        });
        final String message = "The following errors ocurred while running the specification";
        final SpecificationFailureException exception = new SpecificationFailureException(message, new AggregatingException(message, realErrors));
        final SpecificationRunStatus status = new SpecificationRunFailureStatus(exception, realSpecificationError);
        return new SpecificationResultImpl(specificationMethod, status, clock.getElapsedTime(startTime));
    }

    private Throwable realError(final Throwable lifecycleError) {
        return realErrors(List.<Throwable>nil().cons(lifecycleError)).head();
    }

    private List<Throwable> realErrors(final List<Throwable> lifecycleErrors) {
        return lifecycleErrors.bind(new F<Throwable, List<Throwable>>() {
            public List<Throwable> f(final Throwable throwable) {
                if (throwable instanceof AggregatingException) {
                    return realErrors(((AggregatingException) throwable).getAggregatedErrors());
                } else {
                    return List.<Throwable>nil().cons(exceptionSanitiser.sanitise(exceptionFinder.getRootCause(throwable)));
                }
            }
        });
    }

    @Suggest({"Push the obtaining of the lifecycle into the specification builder", "Groups that don't run get the no-op lifecycle"})
    private SpecificationLifecycle lifecycle(final AnnotatedElement contextClass) {
        final Class<? extends SpecificationLifecycle> lifecycleClass =
                contextClass.isAnnotationPresent(Context.class) ? contextClass.getAnnotation(Context.class).lifecycle()
                                                                : StandardSpecificationLifecycle.class;
        return constructorInvoker.invokeNullaryConstructor(lifecycleClass);
    }

    private Validation<Throwable, Unit> validate(final Option<Throwable> option) {
        return validation(option.toEither(unit()).swap());
    }

    private Either<Throwable, Unit> rightToUnit(final Either<Throwable, ?> either) {
        return either.isLeft() ? Either.<Throwable, Unit>left(either.left().value()) : Either.<Throwable, Unit>right(unit());
    }

    private Semigroup<NonEmptyList<Throwable>> throwables() {
        return Semigroup.nonEmptyListSemigroup();
    }
}
