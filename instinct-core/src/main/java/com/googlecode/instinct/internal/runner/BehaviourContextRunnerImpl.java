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
import com.googlecode.instinct.internal.aggregate.locate.MethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MethodLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextRunnerImpl implements BehaviourContextRunner {
    private final MethodLocator methodLocator = new MethodLocatorImpl();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public <T> void run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        final Method[] specificationMethods = getMethods(behaviourContextClass, Specification.class, new BehaviourContextNamingConvention());
        final Method[] beforeSpecificationMethods = getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
        runSpecifications(behaviourContextClass, specificationMethods, beforeSpecificationMethods, afterSpecificationMethods);
    }

    private <T> void runSpecifications(final Class<T> behaviourContextClass, final Method[] specificationMethods,
            final Method[] beforeSpecificationMethods, final Method[] afterSpecificationMethods) {
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = new SpecificationContextImpl(
                    behaviourContextClass, beforeSpecificationMethods, afterSpecificationMethods, specificationMethod);
            specificationRunner.run(specificationContext);
        }
    }

    private <T> Method[] getMethods(final Class<T> behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass, annotationType, namingConvention);
    }
}
