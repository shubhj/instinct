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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

@Suggest("Breadcrumb - Write this.")
public final class StandardContextRunnerAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ContextRunner contextRunner;
    @Mock private SpecificationRunner specificationRunner;
    @Mock private ContextClass contextClass;

    @Override
    public void setUpSubject() {
        contextRunner = createSubject(StandardContextRunner.class, specificationRunner);
    }

    public void testConformsToClassTraits() {
        checkClass(StandardContextRunner.class, ContextRunner.class);
    }

    public void testRunsContextClasses() {
//        contextRunner.run(contextClass);
    }
}
