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
import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BehaviourContextNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextRunnerImpl implements BehaviourContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public <T> BehaviourContextResult run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        final Method[] specificationMethods = getMethods(behaviourContextClass, Specification.class, new BehaviourContextNamingConvention());
        final Method[] beforeSpecificationMethods = getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
        return runSpecifications(behaviourContextClass, specificationMethods, beforeSpecificationMethods, afterSpecificationMethods);
    }

    private <T> BehaviourContextResult runSpecifications(final Class<T> behaviourContextClass, final Method[] specificationMethods,
            final Method[] beforeSpecificationMethods, final Method[] afterSpecificationMethods) {
        final BehaviourContextResult behaviourContextResult = new BehaviourContextResultImpl(behaviourContextClass.getSimpleName());
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = new SpecificationContextImpl(behaviourContextClass, beforeSpecificationMethods,
                    afterSpecificationMethods, specificationMethod);
            behaviourContextResult.addSpecificationResult(specificationRunner.run(specificationContext));
        }
        return behaviourContextResult;
    }

    private <T> Method[] getMethods(final Class<T> behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass, annotationType, namingConvention);
    }
}
