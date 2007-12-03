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

package com.googlecode.instinct.internal.locate;

import com.googlecode.instinct.defect.defect8.AContext;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Method;
import java.util.Collection;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class AnAnnotatedMethodLocatorContext {
    private AnnotatedMethodLocator locator = new AnnotatedMethodLocatorImpl();

    @Specification
    public void shouldBeAnAnnotatedMethodLocator() {
        checkClass(AnnotatedMethodLocatorImpl.class, AnnotatedMethodLocator.class);
    }

    @Specification
    public void shouldNotReturnMethodsThatDoNotMatchTheSuppliedCriteria() {
        final Collection<Method> methods = locator.locate(WithoutRuntimeAnnotations.class, Specification.class);
        expect.that(methods).hasSize(0);
    }

    @Specification
    public void shouldReturnMethodsThatMatchTheSuppliedCriteria() {
        final Collection<Method> methods = locator.locate(WithRuntimeAnnotations.class, Specification.class);
        expect.that(methods).hasSize(2);
    }

    @Specification
    public void shouldReturnAMethodThatMatchesTheSuppliedCriteriaInASuperClass() {
        final Collection<Method> methods = locator.locate(AContext.class, BeforeSpecification.class);
        expect.that(methods).hasSize(1);
        expect.that(methods.iterator().next().getName()).equalTo("setup");
    }

//    @Specification
//    public void shouldReturnAnotherMethodThatMatchesTheSuppliedCriteriaInASuperClass() {
//        final Collection<Method> methods = locator.locate(AContext.class, Specification.class);
//        expect.that(methods).hasSize(2);
//
//        final Iterator<Method> methodIterator = methods.iterator();
//        expect.that(methodIterator.next().getName()).equalTo("shouldFailIfBeforeSpecificationWasNotCalled");
//        expect.that(methodIterator.next().getName()).equalTo("shouldEquateTrueToTrue");
//    }
}