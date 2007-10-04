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

package com.googlecode.instinct.integrate.junit4;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.edge.org.junit.runner.DescriptionEdge;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubjectWithConstructorArgs;
import java.util.Collection;
import java.util.HashSet;
import org.jmock.Expectations;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@SuppressWarnings({"UnusedDeclaration", "unchecked"})
public final class SpecificationRunnerImplAtomicTest extends InstinctTestCase {
    private SpecificationRunner specificationRunner;
    @Mock private Collection<SpecificationMethod> specificationMethods;
    private Description description;
    @Mock private DescriptionEdge descriptionEdge;
    @Mock private SpecificationMethod specificationMethod;
    @Mock private SpecificationResult specificationResult;
    @Mock private ExceptionFinder exceptionFinder;
    @Mock private Failure failure;
    @Mock private SpecificationRunStatus specificationRunStatus;
    @Mock private Throwable exception;
    @Mock private ObjectFactory objectFactory;
    @Mock private Throwable rootCause;
    @Mock private RunNotifier notifier;

    @SuppressWarnings({"serial", "ClassExtendsConcreteCollection", "CloneableClassWithoutClone"})
    @Override
    public void setUpTestDoubles() {
        specificationMethods = new HashSet<SpecificationMethod>() {
            {
                add(specificationMethod);
            }
        };
    }

    @Override
    public void setUpSubject() {
        description = Description.createTestDescription(String.class, "dontCare");
        specificationRunner = createSubjectWithConstructorArgs(SpecificationRunnerImpl.class, new Object[]{notifier}, descriptionEdge, exceptionFinder, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationRunnerImpl.class, SpecificationRunner.class);
    }

    public void testRunsSpecificationSuccessfully() {
        setUpCommonExpectations();
        expect.that(new Expectations() {
            {
                one(specificationResult).completedSuccessfully(); will(returnValue(true));
                one(notifier).fireTestFinished(description);
            }
        });
        specificationRunner.run(specificationMethods);
    }

    public void testRunsSpecificationUnsuccessfully() {
        setUpCommonExpectations();
        expect.that(new Expectations() {
            {
                one(specificationResult).completedSuccessfully(); will(returnValue(false));
                one(specificationResult).getStatus(); will(returnValue(specificationRunStatus));
                one(specificationRunStatus).getDetailedStatus(); will(returnValue(exception));
                one(exceptionFinder).getRootCause(with(any(Throwable.class))); will(returnValue(rootCause));
                one(objectFactory).create(with(same(Failure.class)), with(equal(new Object[]{description, rootCause}))); will(returnValue(failure));
                one(notifier).fireTestFailure(failure);
            }
        });
        specificationRunner.run(specificationMethods);
    }

    private void setUpCommonExpectations() {
        expect.that(new Expectations() {
            {
                one(specificationMethod).getName(); will(returnValue("dontCare"));
                one(specificationMethod).getDeclaringClass(); will(returnValue(String.class));
                atLeast(1).of(specificationMethod).isPending(); will(returnValue(false));
                one(descriptionEdge).createTestDescription(String.class, "dontCare"); will(returnValue(description));
                one(notifier).fireTestStarted(description);
                one(specificationMethod).run(); will(returnValue(specificationResult));
            }
        });
    }
}
