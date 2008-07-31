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
import static com.googlecode.instinct.internal.runner.ErrorLocation.SPECIFICATION;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Mock;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import org.jmock.Expectations;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@SuppressWarnings({"UnusedDeclaration", "unchecked", "TypeMayBeWeakened"})
public final class JUnit4SpecificationRunnerImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private JUnit4SpecificationRunner junit4SpecificationRunner;
    @Mock private List<SpecificationMethod> specificationMethods;
    @Mock private DescriptionEdge descriptionEdge;
    @Mock private SpecificationMethod specificationMethod;
    @Mock private SpecificationResult specificationResult;
    @Mock private ExceptionFinder exceptionFinder;
    @Mock private Failure failure;
    @Mock private Throwable exception;
    @Mock private ObjectFactory objectFactory;
    @Mock private Throwable rootCause;
    @Mock private RunNotifier notifier;
    @Mock private SpecificationRunStatus pendingRunStatus;
    @Stub(auto = false) private SpecificationRunStatus specificationRunStatus;
    @Stub(auto = false) private Description description;

    @Override
    public void setUpTestDoubles() {
        specificationMethods = List.<SpecificationMethod>nil().cons(specificationMethod);
        specificationRunStatus = new SpecificationRunFailureStatus(exception, SPECIFICATION);
    }

    @Override
    public void setUpSubject() {
        description = Description.createTestDescription(String.class, "dontCare");
        junit4SpecificationRunner =
                createSubjectWithConstructorArgs(JUnit4SpecificationRunnerImpl.class, new Object[]{notifier}, descriptionEdge, exceptionFinder,
                        objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(JUnit4SpecificationRunnerImpl.class, JUnit4SpecificationRunner.class);
    }

    public void testRunsSpecificationSuccessfully() {
        setUpCommonExpectations();
        expect.that(new Expectations() {
            {
                one(specificationResult).completedSuccessfully();
                will(returnValue(true));
                ignoring(specificationResult).getStatus();
                one(notifier).fireTestFinished(description);
            }
        });
        junit4SpecificationRunner.run(specificationMethods);
    }

    public void testFiresIgnoredTestsForPendingSpecifications() {
        setUpCommonExpectations();
        expect.that(new Expectations() {
            {
                one(specificationResult).completedSuccessfully();
                will(returnValue(true));
                one(specificationResult).getStatus();
                will(returnValue(pendingRunStatus));
                one(pendingRunStatus).getDetails();
                will(returnValue(PENDING));
                one(notifier).fireTestIgnored(description);
            }
        });
        junit4SpecificationRunner.run(specificationMethods);
    }

    public void testRunsSpecificationUnsuccessfully() {
        setUpCommonExpectations();
        expect.that(new Expectations() {
            {
                one(specificationResult).completedSuccessfully();
                will(returnValue(false));
                one(specificationResult).getStatus();
                will(returnValue(specificationRunStatus));
                one(exceptionFinder).getRootCause(with(any(Throwable.class)));
                will(returnValue(rootCause));
                one(objectFactory).create(with(same(Failure.class)), with(equal(new Object[]{description, rootCause})));
                will(returnValue(failure));
                one(notifier).fireTestFailure(failure);
            }
        });
        junit4SpecificationRunner.run(specificationMethods);
    }

    private void setUpCommonExpectations() {
        expect.that(new Expectations() {
            {
                one(specificationMethod).getName();
                will(returnValue("dontCare"));
                one(specificationMethod).getContextClass();
                will(returnValue(String.class));
                one(descriptionEdge).createTestDescription(String.class, "dontCare");
                will(returnValue(description));
                one(notifier).fireTestStarted(description);
                one(specificationMethod).run();
                will(returnValue(specificationResult));
            }
        });
    }
}
