/*
 * Copyright 2006-2007 Chris Myers, Workingmouse
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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.AContextWithAnnotationsAndNamingConventions;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.matcher.MethodMatcher;
import fj.data.List;
import java.lang.reflect.Method;
import org.hamcrest.Matcher;

@SuppressWarnings({"unchecked"})
public final class MarkedMethodLocatorImplSlowTest extends InstinctTestCase {
    private MarkedMethodLocator locator;
    private MarkingScheme markingScheme;

    @Override
    public void setUpTestDoubles() {
        markingScheme = new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), IGNORE);
    }

    @Override
    public void setUpSubject() {
        locator = new MarkedMethodLocatorImpl();
    }

    public void testFindsNamingConventionMethodsInASimpleNamingConventionContext() {
        final List<Method> methods = locate(ASimpleNamingConventionContext.class);
        expect.that(methods).isOfSize(2);
        expect.that(methods).containsItem(aMethodNamed("mustAlwaysReturnTrue"));
        expect.that(methods).containsItem(aMethodNamed("shouldAlwaysReturnFalse"));
    }

    public void testFindsSpecMethodsInAClassContainingNamedMethodsAndAnnotatedMethods() {
        final List<Method> methods = locate(AContextWithAnnotationsAndNamingConventions.class);
        expect.that(methods).isOfSize(3);
        expect.that(methods).containsItem(aMethodNamed("mustDoSomethingRatherVague"));
        expect.that(methods).containsItem(aMethodNamed("doSomeCrazyRequirement"));
        expect.that(methods).containsItem(aMethodNamed("shouldDoSomethingReallyImportant"));
    }

    private <T> List<Method> locate(final Class<T> contextClass) {
        return locator.locateAll(contextClass, markingScheme);
    }

    private Matcher<Method> aMethodNamed(final String methodName) {
        return new MethodMatcher(methodName);
    }
}
