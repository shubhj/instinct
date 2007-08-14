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

import static com.googlecode.instinct.expect.Mocker12.mock;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.reflect.SubjectCreator;
import org.junit.runner.notification.RunNotifier;

public final class SpecificationRunnerImplAtomicTest extends InstinctTestCase {
    private SpecificationRunner specificationRunner;
    private RunNotifier notifier;
    private SpecificationMethod specificationMethod;

    @Override
    public void setUpTestDoubles() {
    }

    @Override
    public void setUpSubject() {
        notifier = mock(RunNotifier.class);
        specificationRunner = SubjectCreator.createSubjectWithConstructorArgs(SpecificationRunnerImpl.class, new Object[]{notifier});
        specificationMethod = mock(SpecificationMethod.class);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationRunnerImpl.class, SpecificationRunner.class);
    }

//    public void testRunsSpecification() {
//        expects(notifier).method("fireTestStarted").with()
//        specificationRunner.run(specificationMethod);
//    }
}