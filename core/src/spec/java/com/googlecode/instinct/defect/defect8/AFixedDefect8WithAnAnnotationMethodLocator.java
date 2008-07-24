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

package com.googlecode.instinct.defect.defect8;

import com.googlecode.instinct.defect.defect8.data.annotation.AContext;
import com.googlecode.instinct.defect.defect8.data.annotation.ASubContextOfAnAccessRestrictedContext;
import com.googlecode.instinct.defect.defect8.data.annotation.AnotherContext;
import com.googlecode.instinct.defect.defect8.data.annotation.StaticSubContext;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.locate.method.AnnotatedMethodLocator;
import com.googlecode.instinct.internal.locate.method.AnnotatedMethodLocatorImpl;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import fj.F;
import fj.data.List;
import java.lang.reflect.Method;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AFixedDefect8WithAnAnnotationMethodLocator {
    private final AnnotatedMethodLocator locator = new AnnotatedMethodLocatorImpl();

    @Specification
    public void shouldReturnABeforeSpecificationDefinedInABaseClass() {
        final List<Method> methods = locator.locate(AContext.class, BeforeSpecification.class);
        expect.that(methods).isOfSize(1);
        expect.that(methods.head().getName()).isEqualTo("setup");
    }

    @Specification
    public void shouldReturnAnAfterSpecificationDefinedInABaseClass() {
        final List<Method> methods = locator.locate(AContext.class, AfterSpecification.class);
        expect.that(methods).isOfSize(1);
        expect.that(methods.head().getName()).isEqualTo("tearDown");
    }

    @Specification
    public void shouldReturnSpecificationsInAContextAndItsBaseClasses() {
        final List<Method> methods = locator.locate(AnotherContext.class, Specification.class);
        expect.that(methods).isOfSize(2);
        expect.that(methods).contains(new F<Method, Boolean>() {
            public Boolean f(final Method method) {
                return method.getName().equals("shouldEquateTrueToTrue");
            }
        });
        expect.that(methods).contains(new F<Method, Boolean>() {
            public Boolean f(final Method method) {
                return method.getName().equals("shouldEquateFalseToFalse");
            }
        });
    }

    @Specification
    public void shouldReturnStaticSpecificationsInAContextAndItsBasesClasses() {
        final List<Method> methods = locator.locate(StaticSubContext.class, Specification.class);
        expect.that(methods).isOfSize(2);
    }

    @Specification
    public void shouldReturnSpecificationsOfAllVisibilitiesFromAContextAndItsBaseClasses() {
        final List<Method> methods = locator.locate(ASubContextOfAnAccessRestrictedContext.class, Specification.class);
        expect.that(methods).isOfSize(4);
    }
}
