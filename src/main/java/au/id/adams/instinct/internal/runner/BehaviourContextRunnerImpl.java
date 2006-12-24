package au.id.adams.instinct.internal.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import au.id.adams.instinct.core.annotate.AfterSpecification;
import au.id.adams.instinct.core.annotate.BeforeSpecification;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.core.naming.AfterSpecificationNamingConvention;
import au.id.adams.instinct.core.naming.BeforeSpecificationNamingConvention;
import au.id.adams.instinct.core.naming.BehaviourContextNamingConvention;
import au.id.adams.instinct.core.naming.NamingConvention;
import au.id.adams.instinct.internal.aggregate.locate.MethodLocator;
import au.id.adams.instinct.internal.aggregate.locate.MethodLocatorImpl;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextRunnerImpl implements BehaviourContextRunner {
    private final MethodLocator methodLocator = new MethodLocatorImpl();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public <T> void run(final Class<T> behaviourContextClass) {
        checkNotNull(behaviourContextClass);
        final Method[] specificationMethods = getMethods(behaviourContextClass, Specification.class, new BehaviourContextNamingConvention());
        final Method[] beforeTestMethods = getMethods(behaviourContextClass, BeforeSpecification.class, new BeforeSpecificationNamingConvention());
        final Method[] afterTestmethods = getMethods(behaviourContextClass, AfterSpecification.class, new AfterSpecificationNamingConvention());
        runSpecifications(behaviourContextClass, specificationMethods, beforeTestMethods, afterTestmethods);
    }

    private <T> void runSpecifications(final Class<T> behaviourContextClass, final Method[] specificationMethods, final Method[] setUpMethods,
            final Method[] tearDownMethods) {
        for (final Method specificationMethod : specificationMethods) {
            SpecificationContext specificationContext =
                    new SpecificationContextImpl(behaviourContextClass, setUpMethods, tearDownMethods, specificationMethod);
            specificationRunner.run(specificationContext);
        }
    }

    private <T> Method[] getMethods(Class<T> behaviourContextClass, Class<? extends Annotation> annotationType, NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass, annotationType, namingConvention);
    }
}
