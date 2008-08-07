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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import fj.F;
import fj.F2;
import fj.P1;
import fj.data.Either;
import static fj.data.Either.right;
import fj.data.List;
import fj.data.Option;
import static fj.data.Option.none;
import java.lang.reflect.Field;

/**
 * A specification lifecycle that does nothing, i.e. does not run a specification nor any of its lifecycle steps. This lifecycle is used for
 * specifications belonging to groups that are not being run.
 */
public final class NoOpSpecificationLifecycle implements SpecificationLifecycle {
    private final SpecificationLifecycle standardLifecycle = new StandardSpecificationLifecycle();

    public <T> F<Class<T>, Either<Throwable, ContextClass>> createContext() {
        return standardLifecycle.createContext();
    }

    public P1<Option<Throwable>> resetMockery() {
        return standardLifecycle.resetMockery();
    }

    public F<Object, Either<Throwable, List<Field>>> wireActors() {
        return new F<Object, Either<Throwable, List<Field>>>() {
            public Either<Throwable, List<Field>> f(final Object contextInstance) {
                return right(List.<Field>nil());
            }
        };
    }

    public F2<Object, List<LifecycleMethod>, Option<Throwable>> runBeforeSpecificationMethods() {
        return runLifecycleMethods();
    }

    public F2<Object, SpecificationMethod, Option<Throwable>> runSpecification() {
        return new F2<Object, SpecificationMethod, Option<Throwable>>() {
            public Option<Throwable> f(final Object contextInstance, final SpecificationMethod specificationMethod) {
                return none();
            }
        };
    }

    public F2<Object, List<LifecycleMethod>, Option<Throwable>> runAfterSpecificationMethods() {
        return runLifecycleMethods();
    }

    public P1<Option<Throwable>> verifyMocks() {
        return standardLifecycle.verifyMocks();
    }

    private F2<Object, List<LifecycleMethod>, Option<Throwable>> runLifecycleMethods() {
        return new F2<Object, List<LifecycleMethod>, Option<Throwable>>() {
            public Option<Throwable> f(final Object contextInstance, final List<LifecycleMethod> beforeSpecificationMethods) {
                return none();
            }
        };
    }
}
