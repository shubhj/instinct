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

package com.googlecode.instinct.internal.aggregate.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import java.lang.reflect.Method;
import java.util.Collection;

public final class MarkedMethodLocatorImplSlowTest extends InstinctTestCase {

    public void testFindsNamingConventionMethodsInASimpleNamingConventionContext() {
        final NamingConvention namingConvention = new SpecificationNamingConvention();
        final MarkedMethodLocator locator = new MarkedMethodLocatorImpl();
        final Collection<Method> methods = locator.locateAll(ASimpleNamingConventionContext.class,
                new MarkingSchemeImpl(Specification.class, namingConvention));
        expect.that(methods).hasSize(2);
    }
}
