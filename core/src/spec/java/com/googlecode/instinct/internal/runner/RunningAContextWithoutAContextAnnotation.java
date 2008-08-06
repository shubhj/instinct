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
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.reflect.ConstructorInvoker;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.runner.SpecificationLifecycle;
import com.googlecode.instinct.runner.StandardSpecificationLifecycle;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class RunningAContextWithoutAContextAnnotation {
    @Subject(implementation = SpecificationRunnerImpl.class) private SpecificationRunner runner;
    @Mock private ConstructorInvoker constructorInvoker;
    @Stub(auto = false) private SpecificationMethod specification;
    @Stub(auto = false) private SpecificationLifecycle lifecycle;

    @BeforeSpecification
    public void before() {
        specification = new ContextClassImpl(WithoutContextAnnotation.class).getSpecificationMethods().head();
        lifecycle = new StandardSpecificationLifecycle();
        runner = createSubject(SpecificationRunnerImpl.class, constructorInvoker);
    }

    @Specification
    public void usesTheDefaultLifecycle() {
        expect.that(new Expectations() {
            {
                one(constructorInvoker).invokeNullaryConstructor(with(equal(StandardSpecificationLifecycle.class)));
                will(returnValue(lifecycle));
                one(constructorInvoker).invokeNullaryConstructor(with(equal(WithoutContextAnnotation.class)));
            }
        });
        runner.run(specification);
    }

    private static final class WithoutContextAnnotation {
        @Specification
        public void spec() {
        }
    }
}