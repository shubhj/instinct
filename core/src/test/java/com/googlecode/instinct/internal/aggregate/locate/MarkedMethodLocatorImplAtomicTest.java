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
import com.googlecode.instinct.internal.matcher.MethodMatcher;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import java.lang.reflect.Method;
import java.util.Collection;
import org.hamcrest.Matcher;

public final class MarkedMethodLocatorImplAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
//        ClassChecker.checkClass(MarkedMethodLocatorImpl.class, MarkedMethodLocator.class);
    }

    public void testFindsAnnotatedMethodsInASimpleContext() {
        final MarkedMethodLocator locator = new MarkedMethodLocatorImpl();
        final Collection<Method> methods = locator.locateAll(ASimpleContext.class,
                new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention()));
        final Matcher<Method> methodMatcher = new MethodMatcher("toCheckVerification");
        expect.that(methods).containsItem(methodMatcher);
    }
}
