package com.googlecode.instinct.integrate.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BehaviourContextNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

@Suggest("Rename to testcase")
public final class XxxBehaviourContextTestCase extends TestCase implements Test {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final Class<?> behaviourContextClass;
    private final Method specificationMethod;

    public XxxBehaviourContextTestCase(final Class<?> behaviourContextClass, final Method specificationMethod) {
        super(specificationMethod == null ? "" : specificationMethod.getName());
        checkNotNull(behaviourContextClass, specificationMethod);
        this.behaviourContextClass = behaviourContextClass;
        this.specificationMethod = specificationMethod;
    }

    public void run(final TestResult result) {
        checkNotNull(result);
        try {
            runSpecification(result);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    @Override
    public String toString() {
        return specificationMethod.getName();
    }

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    // Note. This is heavily influenced to the implementation of junit.framework.TestResult.run().
    private void runSpecification(final TestResult result) {

        final Method[] specificationMethods = getMethods(behaviourContextClass, Specification.class, new BehaviourContextNamingConvention());
        final Method[] beforeSpecificationMethods = getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());


        result.startTest(this);
        result.runProtected(this, new ContextProtectable());
        result.endTest(this);
    }

    @SuppressWarnings({"ProhibitedExceptionThrown"})
    private void handleException(final EdgeException e) {
        // Note. Need to dig down as reflection is pushed behind an edge.
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    private <T> Method[] getMethods(final Class<T> behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass, annotationType, namingConvention);
    }

    private static final class ContextProtectable implements Protectable {
//        private final BehaviourContextRunner contextRunner;
//        private final Class<?> specificationClass;

//        private <T> ContextProtectable(final BehaviourContextRunner contextRunner, final Class<T> specificationClass) {
//            this.contextRunner = contextRunner;
//            this.specificationClass = specificationClass;
//        }

        public void protect() {
//            contextRunner.run(specificationClass);
        }
    }
}
