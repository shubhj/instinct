package com.googlecode.instinct.integrate.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.core.naming.SpecificationNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationContextImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class BehaviourContextRunner {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    public BehaviourContextResult run(final BehaviourContextClass behaviourContextClass,
            final BehaviourContextRunStrategy behaviourContextRunStrategy, final SpecificationRunStrategy specificationRunStrategy) {
        checkNotNull(behaviourContextClass, behaviourContextRunStrategy);
        behaviourContextRunStrategy.onBehaviourContext(behaviourContextClass);
        runSpecifications(behaviourContextClass, specificationRunStrategy);
        // fix this.
        return null;
    }

    @Suggest("Return the spec results")
    private void runSpecifications(final BehaviourContextClass behaviourContextClass, final SpecificationRunStrategy specificationRunStrategy) {
        System.out.println("Running specifications");
        final Method[] specificationMethods = getSpecificationMethods(behaviourContextClass);
        System.out.println("specificationMethods = " + specificationMethods.length);
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = createSpecificationContext(behaviourContextClass, specificationMethod);
            // save the results
            System.out.println("Running specification = " + specificationContext);
            new SpecificationMethodImpl(specificationContext).run(specificationRunStrategy);
        }
    }

    private SpecificationContext createSpecificationContext(final BehaviourContextClass behaviourContextClass,
            final Method specificationMethod) {
        final Method[] beforeSpecificationMethods = getBeforeSpecificationMethods(behaviourContextClass);
        final Method[] afterSpecificationMethods = getAfterSpecificationMethods(behaviourContextClass);
        return new SpecificationContextImpl(behaviourContextClass.getClass(), beforeSpecificationMethods,
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

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    private Method[] getMethods(final BehaviourContextClass behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass.getType(), annotationType, namingConvention);
    }
}
