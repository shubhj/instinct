/*
 * Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.core;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.NoNamingConvention;
import fj.data.List;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class ASpecificationMethodBuilderWithNoNamingScheme {
    @Subject(implementation = SpecificationMethodBuilderImpl.class) private SpecificationMethodBuilder specificationMethodBuilder;

    @BeforeSpecification
    public void before() {
        specificationMethodBuilder = new SpecificationMethodBuilderImpl();
    }

    @Specification
    public void buildsNoSpecifications() {
        final List<SpecificationMethod> methods = specificationMethodBuilder.buildSpecificationMethods(DifferentNamingConventions.class);
        expect.that(methods).isEmpty();
    }

    @Specification
    public void buildsNoBeforeSpecifications() {
        final List<LifecycleMethod> methods = specificationMethodBuilder.buildBeforeSpecificationMethods(DifferentNamingConventions.class);
        expect.that(methods).isEmpty();
    }

    @Specification
    public void buildsNoAfterSpecifications() {
        final List<LifecycleMethod> methods = specificationMethodBuilder.buildAfterSpecificationMethods(DifferentNamingConventions.class);
        expect.that(methods).isEmpty();
    }

    @Context(beforeSpecificationNamingConvention = NoNamingConvention.class, afterSpecificationNamingConvention = NoNamingConvention.class,
            specificationNamingConvention = NoNamingConvention.class)
    private static final class DifferentNamingConventions {
        public void before() {
        }

        public void after() {
        }

        public void shouldBeASpec() {
        }
    }
}