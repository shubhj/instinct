package com.googlecode.instinct.internal.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationContextImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;

public final class SpikedContextRunnerImpl implements SpikedContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    @Suggest("Don't return null - Tom -> up to here.")
    public ContextResult run(final SpikedContextClass contextClass, final SpikedSpecificationListener specificationListener) {
        checkNotNull(contextClass, specificationListener);
        runSpecifications(contextClass, specificationListener);
        // fix this.
        return null;
    }

    @Suggest("Return the spec results")
    private void runSpecifications(final SpikedContextClass contextClass, final SpikedSpecificationListener specificationListener) {
        final Method[] specificationMethods = getSpecificationMethods(contextClass);
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = createSpecificationContext(contextClass, specificationMethod);
            // save the results
            new SpikedSpecificationMethodImpl(specificationContext).run(specificationListener);
        }
    }

    @Suggest("Pull out into specification context creator.")
    private SpecificationContext createSpecificationContext(final SpikedContextClass contextClass,
            final Method specificationMethod) {
        final Method[] beforeSpecificationMethods = getBeforeSpecificationMethods(contextClass);
        final Method[] afterSpecificationMethods = getAfterSpecificationMethods(contextClass);
        return new SpecificationContextImpl(contextClass.getType(), beforeSpecificationMethods,
                afterSpecificationMethods, specificationMethod);
    }

    private Method[] getBeforeSpecificationMethods(final SpikedContextClass contextClass) {
        return getMethods(contextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
    }

    private Method[] getAfterSpecificationMethods(final SpikedContextClass contextClass) {
        return getMethods(contextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
    }

    private Method[] getSpecificationMethods(final SpikedContextClass contextClass) {
        return getMethods(contextClass, Specification.class, new SpecificationNamingConvention());
    }

    @Suggest("This contains heavy duplication with StandardContextRunner, figure out how to remove it")
    private Method[] getMethods(final SpikedContextClass contextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        final Collection<Method> methods = methodLocator.locateAll(contextClass.getType(), new MarkingSchemeImpl(annotationType, namingConvention));
        return methods.toArray(new Method[methods.size()]);
    }
}
