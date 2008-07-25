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
import fj.data.List;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class ASpecificationMethodBuilderWithNonDefaultAnnotations {
    @Subject(implementation = SpecificationMethodBuilderImpl.class) private SpecificationMethodBuilder specificationMethodBuilder;

    @BeforeSpecification
    public void before() {
        specificationMethodBuilder = new SpecificationMethodBuilderImpl();
    }

    @Specification
    public void buildsSpecifications() {
        final List<SpecificationMethod> methods = specificationMethodBuilder.buildSpecificationMethods(DifferentAnnotations.class);
        expect.that(methods).isOfSize(1);
    }

    @Specification
    public void buildsBeforeSpecifications() {
        final List<LifecycleMethod> methods = specificationMethodBuilder.buildBeforeSpecificationMethods(DifferentAnnotations.class);
        expect.that(methods).isOfSize(1);
    }

    @Specification
    public void buildsAfterSpecifications() {
        final List<LifecycleMethod> methods = specificationMethodBuilder.buildAfterSpecificationMethods(DifferentAnnotations.class);
        expect.that(methods).isOfSize(1);
    }

    @Context(beforeSpecificationAnnotation = NonDefaultBefore.class, afterSpecificationAnnotation = NonDefaultAfter.class,
            specificationAnnotation = NonDefaultSpec.class)
    private static final class DifferentAnnotations {
        @NonDefaultBefore
        public void nonNamedBefore() {
        }

        @NonDefaultAfter
        public void nonNamedafter() {
        }

        @NonDefaultSpec
        public void mightBeASpec() {
        }
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    private @interface NonDefaultBefore {
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    private @interface NonDefaultAfter {
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    private @interface NonDefaultSpec {
    }
}