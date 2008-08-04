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
    @Subject(implementation = NewSpecificationRunnerImpl.class) private SpecificationRunner runner;
    @Mock private ConstructorInvoker constructorInvoker;
    @Stub(auto = false) private SpecificationMethod specification;
    @Stub(auto = false) private SpecificationLifecycle lifecycle;

    @BeforeSpecification
    public void before() {
        specification = new ContextClassImpl(WithContextAnnotation.class).getSpecificationMethods().head();
        lifecycle = new CustomLifecycle();
        runner = createSubject(NewSpecificationRunnerImpl.class, constructorInvoker);
    }

    @Specification
    public void usesTheDefaultLifecycle() {
        expect.that(new Expectations() {
            {
                one(constructorInvoker).invokeNullaryConstructor(with(equal(CustomLifecycle.class)));
                will(returnValue(lifecycle));
                one(constructorInvoker).invokeNullaryConstructor(with(equal(WithContextAnnotation.class)));
            }
        });
        runner.run(specification);
    }

    @Context(lifecycle = CustomLifecycle.class)
    private static final class WithContextAnnotation {
        @Specification
        public void spec() {
        }
    }

    private static final class CustomLifecycle implements SpecificationLifecycle {
        public <T> Either<Throwable, ContextClass> createContext(final Class<T> contextClass) {
            return right((ContextClass) new ContextClassImpl(WithContextAnnotation.class));
        }

        public Option<Throwable> resetMockery() {
            return none();
        }

        public Either<Throwable, List<Field>> wireActors(final Object contextInstance) {
            return right(List.<Field>nil());
        }

        public Option<Throwable> runBeforeSpecificationMethods(final Object contextInstance, final List<LifecycleMethod> beforeSpecificationMethods) {
            return none();
        }

        public Option<Throwable> runSpecification(final Object contextInstance, final SpecificationMethod specificationMethod) {
            return none();
        }

        public Option<Throwable> runAfterSpecificationMethods(final Object contextInstance, final List<LifecycleMethod> afterSpecificationMethods) {
            return none();
        }

        public Option<Throwable> verifyMocks() {
            return none();
        }
    }
}