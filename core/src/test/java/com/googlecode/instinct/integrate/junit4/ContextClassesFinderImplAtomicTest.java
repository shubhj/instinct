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
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ASuiteWithAContext;
import com.googlecode.instinct.internal.runner.ASuiteWithEmptyContextClassesAnnotation;
import com.googlecode.instinct.internal.runner.JUnit4SuiteWithContextAnnotation;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.Collection;

public final class ContextClassesFinderImplAtomicTest extends InstinctTestCase {
    private ContextClassesFinder finder;

    public void testConformsToClassTraits() {
        checkClass(ContextClassesFinderImpl.class, ContextClassesFinder.class);
    }

    @Override
    public void setUpSubject() {
        finder = new ContextClassesFinderImpl();
    }

    public void testReturnsContextClassesWhenAnnotationFound() {
        final Collection<Class<?>> contexts = finder.getContextClasses(JUnit4SuiteWithContextAnnotation.class);
        expect.that(contexts).containsItem(ASimpleContext.class);
    }

    public void testReturnsSuiteClassWhenNoAnnotationFound() {
        final Collection<Class<?>> classes = finder.getContextClasses(ASuiteWithAContext.class);
        expect.that(classes).isOfSize(1);
        expect.that(classes).containsItem(ASuiteWithAContext.class);
    }

    public void testReturnsEmptyCollectionWhenEmptyAnnotationIsFound() {
        final Collection<Class<?>> classes = finder.getContextClasses(ASuiteWithEmptyContextClassesAnnotation.class);
        expect.that(classes).isNotNull();
        expect.that(classes).isEmpty();
    }
}
