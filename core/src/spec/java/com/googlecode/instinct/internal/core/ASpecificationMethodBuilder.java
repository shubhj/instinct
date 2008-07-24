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
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import fj.Effect;
import fj.F;
import fj.data.List;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class ASpecificationMethodBuilder {
    @Subject(implementation = SpecificationMethodBuilderImpl.class) private SpecificationMethodBuilder specificationMethodBuilder;

    @BeforeSpecification
    public void before() {
        specificationMethodBuilder = new SpecificationMethodBuilderImpl();
    }

    @Specification
    public void canBuildAPendingSpecification() {
        final List<SpecificationMethod> specs = specificationMethodBuilder.buildSpecificationMethods(Context.class);
        expect.that(specs).contains(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method instanceof PendingSpecificationMethod;
            }
        });
        specs.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod method) {
                expect.that(method.getBeforeSpecificationMethods()).isOfSize(1);
                expect.that(method.getAfterSpecificationMethods()).isOfSize(1);
            }
        });
    }

    @Specification
    public void canBuildACompleteSpecification() {
        final List<SpecificationMethod> specs = specificationMethodBuilder.buildSpecificationMethods(Context.class);
        expect.that(specs).contains(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method instanceof CompleteSpecificationMethod;
            }
        });
        specs.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod method) {
                expect.that(method.getBeforeSpecificationMethods()).isOfSize(1);
                expect.that(method.getAfterSpecificationMethods()).isOfSize(1);
            }
        });
    }

    @Specification
    public void canBuildAnExceptionSpecification() {
        final List<SpecificationMethod> specs = specificationMethodBuilder.buildSpecificationMethods(Context.class);
        expect.that(specs).contains(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method instanceof ExpectingExceptionSpecificationMethod;
            }
        });
        specs.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod method) {
                expect.that(method.getBeforeSpecificationMethods()).isOfSize(1);
                expect.that(method.getAfterSpecificationMethods()).isOfSize(1);
            }
        });
    }

    @Specification
    public void canBuildSpecsOutOfNamedMethods() {
        final List<SpecificationMethod> specs = specificationMethodBuilder.buildSpecificationMethods(ContextWithNamedMethods.class);
        expect.that(specs).isOfSize(2);
        expect.that(specs).allItemsMatch(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method instanceof CompleteSpecificationMethod;
            }
        });
    }

    private static final class Context {
        @BeforeSpecification
        public void before() {
        }

        @AfterSpecification
        public void after() {
        }

        @Specification(state = PENDING)
        public void pending() {
        }

        @Specification
        public void complete() {
        }

        @Specification(expectedException = RuntimeException.class)
        public void exception() {
        }
    }

    private static final class ContextWithNamedMethods {
        public void shouldDoSomething() {
        }

        public void mustDoSomething() {
        }
    }
}
