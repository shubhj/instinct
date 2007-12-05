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

import com.googlecode.instinct.defect.defect8.data.ASubContextThatExtendsASuperContextWithNamedSpecs;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.locate.NamingConventionMethodLocator;
import com.googlecode.instinct.internal.locate.NamingConventionMethodLocatorImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class AFixedDefect8WithANamingConventionLocator {
    private final NamingConventionMethodLocator locator = new NamingConventionMethodLocatorImpl();

    @Specification
    public void shouldReturnABeforeSpecificationDefinedInABaseClass() {
        final Collection<Method> methods = locator.locate(ASubContextThatExtendsASuperContextWithNamedSpecs.class,
                new BeforeSpecificationNamingConvention());
        expect.that(methods).hasSize(4);
        final List<String> beforeMethodNames = new ArrayList<String>();
        beforeMethodNames.add("setup");
        beforeMethodNames.add("setUp");
        beforeMethodNames.add("givenSomething");
        beforeMethodNames.add("before");

        for (final Method method : methods) {
            expect.that(beforeMethodNames).containsItem(method.getName());
        }
    }
}
