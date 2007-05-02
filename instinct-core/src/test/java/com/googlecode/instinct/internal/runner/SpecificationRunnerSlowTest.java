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

package com.googlecode.instinct.internal.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;

public final class SpecificationRunnerSlowTest extends InstinctTestCase {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private SpecificationRunner runner;

    @Override
    public void setUpSubject() {
        runner = new SpecificationRunnerImpl();
    }

    public void testRunWithSuccess() {
        checkContextsRunWithoutError(ContextContainerWithSetUpAndTearDown.class);
        checkContextsRunWithoutError(ContextWithSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithBeforeSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithAfterSpecificationWithReturnType.class);
    }

    public void testInvalidMethodsBarf() {
        checkInvalidMethodsBarf(ContextWithSpecificationMethodContainingParameter.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedAfterSpecification2.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedBeforeSpecification2.class);
    }

    private <T> void checkContextsRunWithoutError(final Class<T> contextClass) {
        final SpecificationContext[] contexts = findSpecContexts(contextClass);
        for (final SpecificationContext context : contexts) {
            runner.run(context);
        }
    }

    private <T> void checkInvalidMethodsBarf(final Class<T> cls) {
        final SpecificationContext[] contexts = findSpecContexts(cls);
        for (final SpecificationContext context : contexts) {
            final SpecificationResult specificationResult = runner.run(context);
            assertFalse("Context " + context.getContextClass().getSimpleName() + " should have failed",
                    specificationResult.completedSuccessfully());
        }
    }

    private <T> SpecificationContext[] findSpecContexts(final Class<T> contextClass) {
        final Method[] before = getMethods(contextClass, BeforeSpecification.class, new BeforeSpecificationNamingConvention());
        final Method[] after = getMethods(contextClass, AfterSpecification.class, new AfterSpecificationNamingConvention());
        final Method[] specs = getMethods(contextClass, Specification.class, new SpecificationNamingConvention());
        final SpecificationContext[] contexts = new SpecificationContext[specs.length];
        for (int i = 0; i < contexts.length; i++) {
            contexts[i] = new SpecificationContextImpl(contextClass, before, after, specs[i]);
        }
        return contexts;
    }

    private <T, A extends Annotation> Method[] getMethods(final Class<T> cls, final Class<A> annotationType,
            final NamingConvention namingConvention) {
        final Collection<Method> methods = methodLocator.locateAll(cls, new MarkingSchemeImpl(annotationType, namingConvention));
        return methods.toArray(new Method[methods.size()]);
    }
}
