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
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BehaviourContextNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;

@Suggest({"Pass the specification runner to this class, rather than newing it, that way we can pass in logging versions",
        "Figure out a way to break this class up, it's too procedural."})
public final class StandardContextRunner implements ContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public <T> ContextResult run(final Class<T> contextClass) {
        checkNotNull(contextClass);
        final Method[] specificationMethods = getMethods(contextClass, Specification.class, new BehaviourContextNamingConvention());
        final Method[] beforeSpecificationMethods = getMethods(contextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(contextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
        return runSpecifications(contextClass, specificationMethods, beforeSpecificationMethods, afterSpecificationMethods);
    }

    private <T> ContextResult runSpecifications(final Class<T> behaviourContextClass, final Method[] specificationMethods,
            final Method[] beforeSpecificationMethods, final Method[] afterSpecificationMethods) {
        final ContextResult contextResult = new ContextResultImpl(behaviourContextClass.getSimpleName());
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = new SpecificationContextImpl(behaviourContextClass, beforeSpecificationMethods,
                    afterSpecificationMethods, specificationMethod);
            final SpecificationResult specificationResult = specificationRunner.run(specificationContext);
            contextResult.addSpecificationResult(specificationResult);
        }
        return contextResult;
    }

    private <T> Method[] getMethods(final Class<T> behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass, annotationType, namingConvention);
    }
}
