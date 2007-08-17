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

import static com.googlecode.instinct.expect.Mocker12.eq;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.edge.org.junit.runner.DescriptionEdge;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.reflect.SubjectCreator;
import java.util.Collection;
import java.util.HashSet;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public final class SpecificationRunnerImplAtomicTest extends InstinctTestCase {
    private SpecificationRunner specificationRunner;
    private RunNotifier notifier;
    private Collection<SpecificationMethod> specificationMethods;
    private DescriptionEdge descriptionEdge;
    private Description description;
    private SpecificationMethod specificationMethod;
    private SpecificationResult specificationResult;
    private ExceptionFinder exceptionFinder;
    private Failure failure;
    private SpecificationRunStatus specificationRunStatus;
    private Throwable exception;
    private ObjectFactory objectFactory;
    private Throwable rootCause;

    @Override
    public void setUpTestDoubles() {
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void setUpSubject() {
        notifier = mock(RunNotifier.class);
        descriptionEdge = mock(DescriptionEdge.class);
        description = Description.createTestDescription(String.class, "dontCare");
        objectFactory = mock(ObjectFactory.class);
        exceptionFinder = mock(ExceptionFinder.class);
        specificationRunner = SubjectCreator.createSubjectWithConstructorArgs(SpecificationRunnerImpl.class, new Object[]{notifier}, descriptionEdge, exceptionFinder, objectFactory);
        specificationMethod = mock(SpecificationMethod.class);
        specificationMethods = new HashSet<SpecificationMethod>() {
            {
                add(specificationMethod);
            }
        };
        specificationResult = mock(SpecificationResult.class);
        failure = mock(Failure.class);
        specificationRunStatus = mock(SpecificationRunStatus.class);
        exception = mock(Throwable.class);
        rootCause = mock(Throwable.class);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationRunnerImpl.class, SpecificationRunner.class);
    }

    public void testRunsSpecificationSuccessfully() {
        createExpectations();
        expects(specificationResult).method("completedSuccessfully").will(returnValue(true));
        expects(notifier).method("fireTestFinished").with(eq(description));
        specificationRunner.run(specificationMethods);
    }

    public void testRunsSpecificationUnsuccessfully() {
        createExpectations();
        expects(specificationResult).method("completedSuccessfully").will(returnValue(false));
        expects(specificationResult).method("getStatus").will(returnValue(specificationRunStatus));
        expects(specificationRunStatus).method("getDetailedStatus").will(returnValue(exception));
        expects(exceptionFinder).method("getRootCause").will(returnValue(rootCause));
        expects(objectFactory).method("create").with(same(Failure.class), eq(new Object[]{description, rootCause})).will(returnValue(failure));
        expects(notifier).method("fireTestFailure").with(eq(failure));
        specificationRunner.run(specificationMethods);
    }

    private void createExpectations() {
        expects(specificationMethod).method("getName").will(returnValue("dontCare"));
        expects(specificationMethod).method("getDeclaringClass").will(returnValue(String.class));
        expects(descriptionEdge).method("createTestDescription").with(eq(String.class), eq("dontCare")).will(returnValue(description));
        expects(notifier).method("fireTestStarted").with(eq(description));
        expects(specificationMethod).method("run").will(returnValue(specificationResult));
    }
}