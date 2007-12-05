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

import com.googlecode.instinct.defect.defect8.data.naming.ASubContext;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.locate.NamingConventionMethodLocator;
import com.googlecode.instinct.internal.locate.NamingConventionMethodLocatorImpl;
import com.googlecode.instinct.internal.util.TechNote;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.List;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@TechNote("This spec proves that more than one Before/After specification method will be invoked should they exist.")
public class AFixedDefect8WithANamingConventionLocator {
    private final NamingConventionMethodLocator locator = new NamingConventionMethodLocatorImpl();

    @Specification
    public void shouldReturnBeforeSpecificationsDefinedInABaseClass() {
        final List<String> methodList = createMethodList("setup", "setUp", "givenSomething", "before");
        expectNamingConventionIsFollowed(new BeforeSpecificationNamingConvention(), methodList.size(), methodList);
    }

    @Specification
    public void shouldReturnAfterSpecificationsDefinedInABaseClass() {
        final List<String> methodList = createMethodList("tearDown", "teardown", "after");
        expectNamingConventionIsFollowed(new AfterSpecificationNamingConvention(), methodList.size(), methodList);
    }

    @Specification
    public void shouldReturnSpecificationsInAContextAndItsBaseClasses() {
        final List<String> methodList = createMethodList("mustBeCalledFromTheSuperClass", "shouldBeCalledFromTheSuperClass",
                "mustBeCalledFromTheSubClass", "shouldBeCalledFromTheSubClass");
        expectNamingConventionIsFollowed(new SpecificationNamingConvention(), methodList.size(), methodList);
    }

    private void expectNamingConventionIsFollowed(final NamingConvention namingConvention, final int numOfExpectedMethods,
            final List<String> expectedMethodNames) {
        final Collection<Method> locatedMethods =
                locator.locate(ASubContext.class, namingConvention);

        expect.that(locatedMethods).hasSize(numOfExpectedMethods);
        for (final Method aLocatedMethod : locatedMethods) {
            expect.that(expectedMethodNames).containsItem(aLocatedMethod.getName());
        }
    }

    private List<String> createMethodList(final String... methodNames) {
        final List<String> beforeMethodNames = new ArrayList<String>();
        beforeMethodNames.addAll(asList(methodNames));
        return beforeMethodNames;
    }
}
