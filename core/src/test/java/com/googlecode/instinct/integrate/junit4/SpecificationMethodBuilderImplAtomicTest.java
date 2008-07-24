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
import com.googlecode.instinct.internal.runner.JUnit4SuiteWithContextAnnotation;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.F;
import fj.data.List;

public final class SpecificationMethodBuilderImplAtomicTest extends InstinctTestCase {
    private SpecificationMethodBuilder builder;

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodBuilderImpl.class, SpecificationMethodBuilder.class);
    }

    @Override
    public void setUpSubject() {
        builder = createSubject(SpecificationMethodBuilderImpl.class);
    }

    public void testFindsSpecificationsOnClassWithContextClassAnnotation() {
        final List<SpecificationMethod> methods = builder.buildSpecifications(JUnit4SuiteWithContextAnnotation.class);
        expect.that(methods).isOfSize(1);
        expect.that(methods).contains(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method.getName().equals("toCheckVerification");
            }
        });
    }
}
