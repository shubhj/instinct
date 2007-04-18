package com.googlecode.instinct.integrate.junit3;

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

@Suggest("Pull into main packages.")
public final class BehaviourContextRunnerImpl implements BehaviourContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    public ContextResult run(final BehaviourContextClass behaviourContextClass,
            final BehaviourContextRunStrategy behaviourContextRunStrategy, final SpecificationRunStrategy specificationRunStrategy) {
        checkNotNull(behaviourContextClass, behaviourContextRunStrategy);
        behaviourContextRunStrategy.onBehaviourContext(behaviourContextClass);
        runSpecifications(behaviourContextClass, specificationRunStrategy);
        // fix this.
        return null;
    }

    @Suggest("Return the spec results")
    private void runSpecifications(final BehaviourContextClass behaviourContextClass, final SpecificationRunStrategy specificationRunStrategy) {
        final Method[] specificationMethods = getSpecificationMethods(behaviourContextClass);
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = createSpecificationContext(behaviourContextClass, specificationMethod);
            // save the results
            new SpecificationMethodImpl(specificationContext).run(specificationRunStrategy);
        }
    }

    @Suggest("Pull out into specification context creator.")
    private SpecificationContext createSpecificationContext(final BehaviourContextClass behaviourContextClass,
            final Method specificationMethod) {
        final Method[] beforeSpecificationMethods = getBeforeSpecificationMethods(behaviourContextClass);
        final Method[] afterSpecificationMethods = getAfterSpecificationMethods(behaviourContextClass);
        return new SpecificationContextImpl(behaviourContextClass.getType(), beforeSpecificationMethods,
                afterSpecificationMethods, specificationMethod);
    }

    private Method[] getBeforeSpecificationMethods(final BehaviourContextClass behaviourContextClass) {
        return getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
    }

    private Method[] getAfterSpecificationMethods(final BehaviourContextClass behaviourContextClass) {
        return getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
    }

    private Method[] getSpecificationMethods(final BehaviourContextClass behaviourContextClass) {
        return getMethods(behaviourContextClass, Specification.class, new SpecificationNamingConvention());
    }

    @Suggest("This contains heavy duplication with StandardContextRunner, figure out how to remove it")
    private Method[] getMethods(final BehaviourContextClass behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass.getType(), annotationType, namingConvention);
    }
}
