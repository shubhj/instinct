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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.apache.tools.ant.Project;

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class SpecificationsAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private Specifications specifications;
    @Dummy private Project project;

    @Override
    public void setUpSubject() {
        specifications = new Specifications(project);
    }

    public void testConformsToClassTraits() {
        checkClass(Specifications.class);
    }
}