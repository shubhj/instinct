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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.reflect.ConstructorInvoker;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.runner.SpecificationLifecycle;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import fj.F;
import fj.F2;
import fj.P1;
import fj.data.Either;
import static fj.data.Either.right;
import fj.data.List;
import fj.data.Option;
import static fj.data.Option.none;
import java.lang.reflect.Field;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class RunningAContextWithAContextAnnotation {
    @Subject(implementation = SpecificationRunnerImpl.class) private SpecificationRunner runner;
    @Mock private ConstructorInvoker constructorInvoker;
    @Stub(auto = false) private SpecificationMethod specification;
    @Stub(auto = false) private SpecificationLifecycle lifecycle;

    @BeforeSpecification
    public void before() {
        specification = new ContextClassImpl(WithContextAnnotation.class).getSpecificationMethods().head();
        lifecycle = new CustomLifecycle();
        runner = createSubject(SpecificationRunnerImpl.class, constructorInvoker);
    }

    @Specification
    public void usesTheDefaultLifecycle() {
        expect.that(new Expectations() {
            {
                one(constructorInvoker).invokeNullaryConstructor(with(equal(CustomLifecycle.class)));
                will(returnValue(lifecycle));
                one(constructorInvoker).invokeNullaryConstructor(with(equal(WithContextAnnotation.class)));
            }});
        runner.run(specification);
    }

    @Context(lifecycle = CustomLifecycle.class)
    private static final class WithContextAnnotation {
        @Specification
        public void spec() {
        }
    }

    private static final class CustomLifecycle implements SpecificationLifecycle {
        public <T> F<Class<T>, Either<Throwable, ContextClass>> createContext() {
            return new F<Class<T>, Either<Throwable, ContextClass>>() {
                public Either<Throwable, ContextClass> f(final Class<T> contextClass) {
                    return right((ContextClass) new ContextClassImpl(WithContextAnnotation.class));
                }
            };
        }

        public P1<Option<Throwable>> resetMockery() {
            return new P1<Option<Throwable>>() {
                @Override
                public Option<Throwable> _1() {
                    return none();
                }
            };
        }

        public F<Object, Either<Throwable, List<Field>>> wireActors() {
            return new F<Object, Either<Throwable, List<Field>>>() {
                public Either<Throwable, List<Field>> f(final Object contextInstance) {
                    return right(List.<Field>nil());
                }
            };
        }

        public F2<Object, List<LifecycleMethod>, Option<Throwable>> runBeforeSpecificationMethods() {
            return new F2<Object, List<LifecycleMethod>, Option<Throwable>>() {
                public Option<Throwable> f(final Object contextInstance, final List<LifecycleMethod> beforeSpecificationMethods) {
                    return none();
                }
            };
        }

        public F2<Object, SpecificationMethod, Option<Throwable>> runSpecification() {
            return new F2<Object, SpecificationMethod, Option<Throwable>>() {
                public Option<Throwable> f(final Object contextInstance, final SpecificationMethod specificationMethod) {
                    return none();
                }
            };
        }

        public F2<Object, List<LifecycleMethod>, Option<Throwable>> runAfterSpecificationMethods() {
            return new F2<Object, List<LifecycleMethod>, Option<Throwable>>() {
                public Option<Throwable> f(final Object contextInstance, final List<LifecycleMethod> beforeSpecificationMethods) {
                    return none();
                }
            };
        }

        public P1<Option<Throwable>> verifyMocks() {
            return new P1<Option<Throwable>>() {
                @Override
                public Option<Throwable> _1() {
                    return none();
                }
            };
        }
    }
}