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

@SuppressWarnings({"UnusedDeclaration"})
public final class SpecificationRunnerImplAtomicTest extends InstinctTestCase {
    private SpecificationRunner specificationRunner;
    private Collection<SpecificationMethod> specificationMethods;
    private Description description;
    private DescriptionEdge mockDescriptionEdge;
    private SpecificationMethod mockSpecificationMethod;
    private SpecificationResult mockSpecificationResult;
    private ExceptionFinder mockExceptionFinder;
    private Failure mockFailure;
    private SpecificationRunStatus mockSpecificationRunStatus;
    private Throwable mockException;
    private ObjectFactory mockObjectFactory;
    private Throwable mockRootCause;
    private RunNotifier mockNotifier;

    @Override
    public void setUpTestDoubles() {
        setUpAutoMocks();
        specificationMethods = new HashSet<SpecificationMethod>() {
            {
                add(mockSpecificationMethod);
            }
        };
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void setUpSubject() {
        description = Description.createTestDescription(String.class, "dontCare");
        specificationRunner = SubjectCreator.createSubjectWithConstructorArgs(SpecificationRunnerImpl.class, new Object[]{mockNotifier}, mockDescriptionEdge, mockExceptionFinder, mockObjectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationRunnerImpl.class, SpecificationRunner.class);
    }

    public void testRunsSpecificationSuccessfully() {
        createExpectations();
        expects(mockSpecificationResult).method("completedSuccessfully").will(returnValue(true));
        expects(mockNotifier).method("fireTestFinished").with(eq(description));
        specificationRunner.run(specificationMethods);
    }

    public void testRunsSpecificationUnsuccessfully() {
        createExpectations();
        expects(mockSpecificationResult).method("completedSuccessfully").will(returnValue(false));
        expects(mockSpecificationResult).method("getStatus").will(returnValue(mockSpecificationRunStatus));
        expects(mockSpecificationRunStatus).method("getDetailedStatus").will(returnValue(mockException));
        expects(mockExceptionFinder).method("getRootCause").will(returnValue(mockRootCause));
        expects(mockObjectFactory).method("create").with(same(Failure.class), eq(new Object[]{description, mockRootCause})).will(returnValue(mockFailure));
        expects(mockNotifier).method("fireTestFailure").with(eq(mockFailure));
        specificationRunner.run(specificationMethods);
    }

    private void createExpectations() {
        expects(mockSpecificationMethod).method("getName").will(returnValue("dontCare"));
        expects(mockSpecificationMethod).method("getDeclaringClass").will(returnValue(String.class));
        expects(mockDescriptionEdge).method("createTestDescription").with(eq(String.class), eq("dontCare")).will(returnValue(description));
        expects(mockNotifier).method("fireTestStarted").with(eq(description));
        expects(mockSpecificationMethod).method("run").will(returnValue(mockSpecificationResult));
    }
}
