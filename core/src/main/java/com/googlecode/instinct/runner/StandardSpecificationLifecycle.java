/*
 * Copyright 2006-2008 Tom Adams
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

package com.googlecode.instinct.runner;

import com.googlecode.instinct.actor.ActorAutoWirer;
import com.googlecode.instinct.actor.ActorAutoWirerImpl;
import com.googlecode.instinct.expect.behaviour.Mocker;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.AggregatingException;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import fj.F;
import fj.F2;
import fj.P1;
import fj.data.Either;
import static fj.data.Either.left;
import static fj.data.Either.right;
import fj.data.List;
import fj.data.Option;
import static fj.data.Option.none;
import static fj.data.Option.some;
import static fj.data.Option.somes;
import java.lang.reflect.Field;

/**
 * The standard (default) specification lifecycle.
 */
public final class StandardSpecificationLifecycle implements SpecificationLifecycle {
    private final ActorAutoWirer actorAutoWirer = new ActorAutoWirerImpl();
    private final MethodInvoker methodInvoker = new MethodInvokerImpl();

    public <T> F<Class<T>, Either<Throwable, ContextClass>> createContext() {
        return new F<Class<T>, Either<Throwable, ContextClass>>() {
            public Either<Throwable, ContextClass> f(final Class<T> contextClass) {
                checkNotNull(contextClass);
                try {
                    return right((ContextClass) new ContextClassImpl(contextClass));
                } catch (Throwable t) {
                    return left(t);
                }
            }
        };
    }

    public P1<Option<Throwable>> resetMockery() {
        return new P1<Option<Throwable>>() {
            @Override
            public Option<Throwable> _1() {
                try {
                    Mocker.reset();
                    return none();
                } catch (Throwable t) {
                    return some(t);
                }
            }
        };
    }

    public F<Object, Either<Throwable, List<Field>>> wireActors() {
        return new F<Object, Either<Throwable, List<Field>>>() {
            public Either<Throwable, List<Field>> f(final Object contextInstance) {
                checkNotNull(contextInstance);
                try {
                    return right(actorAutoWirer.autoWireFields(contextInstance));
                } catch (Throwable t) {
                    return left(t);
                }
            }
        };
    }

    public F2<Object, List<LifecycleMethod>, Option<Throwable>> runBeforeSpecificationMethods() {
        return runLifecycleMethods();
    }

    public F2<Object, SpecificationMethod, Option<Throwable>> runSpecification() {
        return new F2<Object, SpecificationMethod, Option<Throwable>>() {
            public Option<Throwable> f(final Object contextInstance, final SpecificationMethod specificationMethod) {
                checkNotNull(contextInstance, specificationMethod);
                return runMethod(contextInstance, specificationMethod);
            }
        };
    }

    public F2<Object, List<LifecycleMethod>, Option<Throwable>> runAfterSpecificationMethods() {
        return runLifecycleMethods();
    }

    public P1<Option<Throwable>> verifyMocks() {
        return new P1<Option<Throwable>>() {
            @Override
            public Option<Throwable> _1() {
                try {
                    Mocker.verify();
                    return none();
                } catch (Throwable t) {
                    return some(t);
                }
            }
        };
    }

    private F2<Object, List<LifecycleMethod>, Option<Throwable>> runLifecycleMethods() {
        return new F2<Object, List<LifecycleMethod>, Option<Throwable>>() {
            public Option<Throwable> f(final Object contextInstance, final List<LifecycleMethod> methods) {
                checkNotNull(contextInstance, methods);
                return runMethods(contextInstance, methods);
            }
        };
    }

    private Option<Throwable> runMethods(final Object instance, final List<LifecycleMethod> methods) {
        final List<Option<Throwable>> results = methods.map(new F<LifecycleMethod, Option<Throwable>>() {
            public Option<Throwable> f(final LifecycleMethod method) {
                return runMethod(instance, method);
            }
        });
        final List<Throwable> errors = somes(results);
        final String message = "At least one specification lifecycle method failed to execute";
        return errors.isEmpty() ? Option.<Throwable>none() : some((Throwable) new AggregatingException(message, errors));
    }

    private Option<Throwable> runMethod(final Object instance, final LifecycleMethod method) {
        try {
            methodInvoker.invokeMethod(instance, method.getMethod());
            return none();
        } catch (Throwable t) {
            return some(t);
        }
    }
}
