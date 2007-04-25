package com.googlecode.instinct.internal.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationContextImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;

public final class ContextRunnerImpl implements ContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    @Suggest("Don't return null - Tom -> up to here.")
    public ContextResult run(final ContextClass contextClass, final SpecificationListener specificationListener) {
        checkNotNull(contextClass, specificationListener);
        runSpecifications(contextClass, specificationListener);
        // fix this.
        return null;
    }

    @Suggest("Return the spec results")
    private void runSpecifications(final ContextClass contextClass, final SpecificationListener specificationListener) {
        final Method[] specificationMethods = getSpecificationMethods(contextClass);
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = createSpecificationContext(contextClass, specificationMethod);
            // save the results
            new SpecificationMethodImpl(specificationContext).run(specificationListener);
        }
    }

    @Suggest("Pull out into specification context creator.")
    private SpecificationContext createSpecificationContext(final ContextClass contextClass,
            final Method specificationMethod) {
        final Method[] beforeSpecificationMethods = getBeforeSpecificationMethods(contextClass);
        final Method[] afterSpecificationMethods = getAfterSpecificationMethods(contextClass);
        return new SpecificationContextImpl(contextClass.getType(), beforeSpecificationMethods,
                afterSpecificationMethods, specificationMethod);
    }

    private Method[] getBeforeSpecificationMethods(final ContextClass contextClass) {
        return getMethods(contextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
    }

    private Method[] getAfterSpecificationMethods(final ContextClass contextClass) {
        return getMethods(contextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
    }

    private Method[] getSpecificationMethods(final ContextClass contextClass) {
        return getMethods(contextClass, Specification.class, new SpecificationNamingConvention());
    }

    @Suggest("This contains heavy duplication with StandardContextRunner, figure out how to remove it")
    private Method[] getMethods(final ContextClass contextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(contextClass.getType(), annotationType, namingConvention);
    }
}
