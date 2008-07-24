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

import com.googlecode.instinct.internal.locate.method.MarkedMethodLocator;
import com.googlecode.instinct.internal.locate.method.MarkedMethodLocatorImpl;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Specification.NoExpectedException;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import fj.F;
import fj.data.List;
import java.lang.reflect.Method;

public final class SpecificationMethodBuilderImpl implements SpecificationMethodBuilder {
    private static final Class<NoExpectedException> NO_EXPECTED_EXCEPTION = NoExpectedException.class;
    private static final MarkingScheme SPECIFICATION = new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), IGNORE);
    private static final MarkingScheme BEFORE_SPECIFICATION =
            new MarkingSchemeImpl(BeforeSpecification.class, new BeforeSpecificationNamingConvention(), IGNORE);
    private static final MarkingScheme AFTER_SPECIFICATION =
            new MarkingSchemeImpl(AfterSpecification.class, new AfterSpecificationNamingConvention(), IGNORE);
    private MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    public <T> List<LifecycleMethod> buildBeforeSpecificationMethods(final Class<T> contextType) {
        return methodLocator.locateAll(contextType, BEFORE_SPECIFICATION).map(lifecycleMethod(contextType));
    }

    public <T> List<LifecycleMethod> buildAfterSpecificationMethods(final Class<T> contextType) {
        return methodLocator.locateAll(contextType, AFTER_SPECIFICATION).map(lifecycleMethod(contextType));
    }

    public <T> List<SpecificationMethod> buildSpecificationMethods(final Class<T> contextType) {
        return methodLocator.locateAll(contextType, SPECIFICATION).map(specificationMethod(contextType));
    }

    private <T> F<Method, LifecycleMethod> lifecycleMethod(final Class<T> contextType) {
        return new F<Method, LifecycleMethod>() {
            public LifecycleMethod f(final Method a) {
                return new LifecycleMethodImpl(a, contextType);
            }
        };
    }

    private <T> F<Method, SpecificationMethod> specificationMethod(final Class<T> contextType) {
        return new F<Method, SpecificationMethod>() {
            public SpecificationMethod f(final Method a) {
                return buildSpecification(contextType, a);
            }
        };
    }

    private <T> SpecificationMethod buildSpecification(final Class<T> contextType, final Method method) {
        if (method.isAnnotationPresent(Specification.class)) {
            final Specification annotation = method.getAnnotation(Specification.class);
            if (annotation.state() == PENDING) {
                return new PendingSpecificationMethod(method, buildBeforeSpecificationMethods(contextType),
                        buildAfterSpecificationMethods(contextType));
            } else if (annotation.expectedException() != NO_EXPECTED_EXCEPTION) {
                return new ExpectingExceptionSpecificationMethod(method, buildBeforeSpecificationMethods(contextType),
                        buildAfterSpecificationMethods(contextType));
            } else {
                return new CompleteSpecificationMethod(method, buildBeforeSpecificationMethods(contextType),
                        buildAfterSpecificationMethods(contextType));
            }
        } else {
            return new CompleteSpecificationMethod(method, buildBeforeSpecificationMethods(contextType), buildAfterSpecificationMethods(contextType));
        }
    }
}
