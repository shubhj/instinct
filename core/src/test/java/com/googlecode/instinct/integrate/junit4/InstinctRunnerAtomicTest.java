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
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.Collection;
import org.jmock.Expectations;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

@SuppressWarnings({"unchecked"})
public final class InstinctRunnerAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private Runner runner;
    @Mock private SpecificationRunner specificationRunner;
    @Mock private ObjectFactory objectFactory;
    @Stub(auto = false) private Collection<SpecificationMethod> specificationMethods;
    @Dummy RunNotifier runNotifier;

    @Override
    public void setUpSubject() {
        runner = createSubjectWithConstructorArgs(InstinctRunner.class, new Object[]{ContextToRun.class}, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(InstinctRunner.class);
    }

    public void testCreatesASuiteDescription() {
        final Description expectedDescription = createDescription();
        expect.that(runner.getDescription()).isEqualTo(expectedDescription);
    }

    public void testRunsSuitesContainingContextClasses() {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(with(same(SpecificationRunnerImpl.class)), with(any(RunNotifier.class)));
                will(returnValue(specificationRunner));
                one(specificationRunner).run(with(any(Collection.class)));
            }
        });
        runner.run(runNotifier);
    }

    private Description createDescription() {
        final Description description = Description.createSuiteDescription(ContextToRun.class);
        description.addChild(Description.createTestDescription(ContextToRun.class, "doSomething"));
        return description;
    }

    @SuppressWarnings({"ALL"})
    private static final class ContextToRun {
        @Specification
        public void doSomething() {
        }
    }
}
