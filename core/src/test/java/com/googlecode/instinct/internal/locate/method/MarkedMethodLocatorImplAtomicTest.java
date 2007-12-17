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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.matcher.MethodMatcher;
import com.googlecode.instinct.internal.runner.AContextThatHasAMethodWithAnnotationAndNamingConvention;
import com.googlecode.instinct.internal.runner.AContextWithAnnotationsAndNamingConventions;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Method;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.jmock.Expectations;

@SuppressWarnings({"unchecked"})
@Suggest("This class needs a bit of a tidy up.")
public final class MarkedMethodLocatorImplAtomicTest extends InstinctTestCase {
    @Subject private MarkedMethodLocator locator;
    @Mock private NamingConvention namingConvention;

    public void testConformsToClassTraits() {
        checkClass(MarkedMethodLocatorImpl.class, MarkedMethodLocator.class);
    }

    public void testFindsAnnotatedSpecificationMethodsInASimpleContext() {
        expect.that(specificationMethodsInASimpleContextClass()).containsItem(aMethodNamed("toCheckVerification"));
    }

    public void testFindsSpecificationMethodsConformingToNamingConventionInASimpleNamingConventionContext() {
        final Collection<Method> specificationMethods = getSpecificationMethodsFromContextClass(ASimpleNamingConventionContext.class);
        expect.that(specificationMethods).isOfSize(2);
        expect.that(specificationMethods).containsItems(aMethodNamed("mustAlwaysReturnTrue"), aMethodNamed("shouldAlwaysReturnFalse"));
    }

    public void testFindsBothAnnotatedAndNamedSpecificationMethodsInTheSameClass() {
        final Collection<Method> methods = getSpecificationMethodsFromContextClass(AContextWithAnnotationsAndNamingConventions.class);
        expect.that(methods).isOfSize(3);
        expect.that(methods).containsItems(aMethodNamed("mustDoSomethingRatherVague"), aMethodNamed("doSomeCrazyRequirement"),
                aMethodNamed("shouldDoSomethingReallyImportant"));
    }

    public void testFindsAnnotatedBeforeSpecificationMethodsInASimpleContext() {
        final Collection<Method> methods = getBeforeSpecificationMethodsFromContextClass(ASimpleContext.class, "");
        expect.that(methods).isOfSize(2);
        expect.that(methods).containsItems(aMethodNamed("setUp"), aMethodNamed("setUpAgain"));
    }

    public void testFindsNamingConventionBeforeSpecificationMethodsInASimpleNamingConventionContext() {
        final Collection<Method> methods = getBeforeSpecificationMethodsFromContextClass(ASimpleNamingConventionContext.class, "^before.*");
        expect.that(methods).isOfSize(2);
        expect.that(methods).containsItems(aMethodNamed("beforeSpecification"), aMethodNamed("beforeWeDoStuff"));
    }

    public void testReturnsAUniqueListOfMethodsWhenAMethodHasBothAnnotationAndNamingConvention() {
        final Collection<Method> methods = getSpecificationMethodsFromContextClass(AContextThatHasAMethodWithAnnotationAndNamingConvention.class);
        expect.that(methods).isOfSize(1);
        expect.that(methods).containsItem(aMethodNamed("mustDoSomething"));
    }

    private Collection<Method> specificationMethodsInASimpleContextClass() {
        return getSpecificationMethodsFromContextClass(ASimpleContext.class);
    }

    private Matcher<Method> aMethodNamed(final String methodName) {
        return new MethodMatcher(methodName);
    }

    private <T> Collection<Method> getBeforeSpecificationMethodsFromContextClass(final Class<T> cls, final String namingPattern) {
        expect.that(new Expectations() {
            {
                atLeast(1).of(namingConvention).getPattern();
                will(returnValue(namingPattern));
            }
        });
        return locator.locateAll(cls, new MarkingSchemeImpl(BeforeSpecification.class, namingConvention, IGNORE));
    }

    private <T> Collection<Method> getSpecificationMethodsFromContextClass(final Class<T> cls) {
        expect.that(new Expectations() {
            {
                atLeast(1).of(namingConvention).getPattern();
                will(returnValue("^must.*|^should.*"));
            }
        });
        return locator.locateAll(cls, new MarkingSchemeImpl(Specification.class, namingConvention, IGNORE));
    }
}
