/*
 * Copyright 2006-2007 Workingmouse
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
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubjectWithConstructorArgs;
import java.util.Collection;
import org.jmock.Expectations;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

@SuppressWarnings({"unchecked"})
public final class InstinctRunnerAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_TO_RUN = String.class;
    private Runner runner;
    private RunNotifier runNotifier;
    private SpecificationMethodBuilder specificationMethodBuilder;
    private Collection<SpecificationMethod> specificationMethods;
    private SpecificationRunner specificationRunner;
    private ObjectFactory objectFactory;

    @Override
    public void setUpTestDoubles() {
        runNotifier = mock(RunNotifier.class);
        specificationMethodBuilder = mock(SpecificationMethodBuilder.class);
        specificationMethods = mock(Collection.class);
        specificationRunner = mock(SpecificationRunner.class);
        objectFactory = mock(ObjectFactory.class);
    }

    @Override
    public void setUpSubject() {
        runner = createSubjectWithConstructorArgs(InstinctRunner.class, new Object[]{CLASS_TO_RUN}, specificationMethodBuilder, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(InstinctRunner.class);
    }

    public void testRunsSuitesContainingContextClasses() {
        expect.that(new Expectations() {
            {
                one(specificationMethodBuilder).build(CLASS_TO_RUN); will(returnValue(specificationMethods));
                one(objectFactory).create(SpecificationRunnerImpl.class, runNotifier); will(returnValue(specificationRunner));
                one(specificationRunner).run(specificationMethods);
            }
        });
        runner.run(runNotifier);
    }

    public void testCreatesASuiteDescription() {
        expect.that(runner.getDescription()).isEqualTo(Description.createSuiteDescription(CLASS_TO_RUN));
    }
}
