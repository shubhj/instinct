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

package com.googlecode.instinct.internal.aggregate.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker12.anyTimes;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import com.googlecode.instinct.internal.matcher.MethodMatcher;
import com.googlecode.instinct.internal.runner.AContextThatHasAMethodWithAnnotationAndNamingConvention;
import com.googlecode.instinct.internal.runner.AContextWithAnnotationsAndNamingConventions;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import java.lang.reflect.Method;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.jmock.internal.matcher.MethodNameMatcher;

public final class MarkedMethodLocatorImplAtomicTest extends InstinctTestCase {
    private MarkedMethodLocator locator;
    private NamingConvention namingConvention;

    public void testConformsToClassTraits() {
//        ClassChecker.checkClass(MarkedMethodLocatorImpl.class, MarkedMethodLocator.class);
    }

    @Override
    public void setUpTestDoubles() {
        namingConvention = mock(NamingConvention.class);
    }

    @Override
    public void setUpSubject() {
        super.setUpSubject();
        locator = new MarkedMethodLocatorImpl();
    }

    public void testFindsAnnotatedSpecificationMethodsInASimpleContext() {
        final Collection<Method> specificationMethodsCollection = getSpecificationMethodsFromContextClass(ASimpleContext.class);
        final Matcher<Method> toCheckVerificationMethod = new MethodMatcher("toCheckVerification");
        expect.that(specificationMethodsCollection).containsItem(toCheckVerificationMethod);
    }

    public void testFindsSpecificationMethodsConformingToNamingConventionInASimpleNamingConventionContext() {
        final Collection<Method> specificationMethods = getSpecificationMethodsFromContextClass(ASimpleNamingConventionContext.class);
        expect.that(specificationMethods).hasSize(2);
        final Matcher<Method> aMethodNamedMustAlwaysReturnTrue = new MethodNameMatcher("mustAlwaysReturnTrue");
        final Matcher<Method> aMethodNamedShouldAlwaysReturnFalse = new MethodNameMatcher("shouldAlwaysReturnFalse");
        expect.that(specificationMethods).containsItems(aMethodNamedMustAlwaysReturnTrue, aMethodNamedShouldAlwaysReturnFalse);
    }

    public void testFindsBothAnnotatedAndNamedSpecificationMethodsInTheSameClass() {
        final Collection<Method> methods = getSpecificationMethodsFromContextClass(AContextWithAnnotationsAndNamingConventions.class);
        expect.that(methods).hasSize(3);
        final Matcher<Method> aMethodNamedMustDoSomethingRatherVague = new MethodNameMatcher("mustDoSomethingRatherVague");
        final Matcher<Method> aMethodNamedDoSomeCrazyRequirement = new MethodNameMatcher("doSomeCrazyRequirement");
        final Matcher<Method> aMethodNamedShouldDoSomethingReallyImportant = new MethodNameMatcher("shouldDoSomethingReallyImportant");
        expect.that(methods).containsItems(aMethodNamedMustDoSomethingRatherVague, aMethodNamedDoSomeCrazyRequirement,
                aMethodNamedShouldDoSomethingReallyImportant);
    }

    public void testFindsAnnotatedBeforeSpecificationMethodsInASimpleContext() {
        final Collection<Method> methods = getBeforeSpecificationMethodsFromContextClass("", ASimpleContext.class);
        expect.that(methods).hasSize(2);
        final Matcher<Method> aMethodNamedSetup = new MethodNameMatcher("setUp");
        final Matcher<Method> aMethodNamedSetupAgain = new MethodNameMatcher("setUpAgain");
        expect.that(methods).containsItems(aMethodNamedSetup, aMethodNamedSetupAgain);
    }

    public void testFindsNamingConventionBeforeSpecificationMethodsInASimpleNamingConventionContext() {
        final Collection<Method> methods = getBeforeSpecificationMethodsFromContextClass("^before.*", ASimpleNamingConventionContext.class);
        expect.that(methods).hasSize(2);
        final Matcher<Method> aMethodNamedBeforeSpecification = new MethodNameMatcher("beforeSpecification");
        final Matcher<Method> aMethodNamedBeforeWeDoStuff = new MethodNameMatcher("beforeWeDoStuff");
        expect.that(methods).containsItems(aMethodNamedBeforeSpecification, aMethodNamedBeforeWeDoStuff);
    }

    public void testReturnsAUniqueListOfMethodsWhenAMethodHasBothAnnotationAndNamingConvention() {
        final Collection<Method> methods = getSpecificationMethodsFromContextClass(AContextThatHasAMethodWithAnnotationAndNamingConvention.class);
        expect.that(methods).hasSize(1);
        final Matcher<Method> aMethodNamedMustDoSomething = new MethodNameMatcher("mustDoSomething");
        expect.that(methods).containsItem(aMethodNamedMustDoSomething);
    }

    private <T> Collection<Method> getBeforeSpecificationMethodsFromContextClass(final String namingPattern, final Class<T> cls) {
        expects(namingConvention, anyTimes()).method("getPattern").will(returnValue(namingPattern));
        return locator.locateAll(cls,
                new MarkingSchemeImpl(BeforeSpecification.class, namingConvention));
    }

    private <T> Collection<Method> getSpecificationMethodsFromContextClass(final Class<T> cls) {
        expects(namingConvention, anyTimes()).method("getPattern").will(returnValue("^must.*|^should.*"));
        return locator.locateAll(cls, new MarkingSchemeImpl(Specification.class, namingConvention));
    }
}
