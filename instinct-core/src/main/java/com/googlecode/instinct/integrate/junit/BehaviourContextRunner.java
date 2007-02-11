package com.googlecode.instinct.integrate.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationContextImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.VerificationException;
import junit.framework.AssertionFailedError;
import junit.framework.TestResult;

public final class BehaviourContextRunner {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();

    public BehaviourContextResult run(final BehaviourContextClass behaviourContextClass,
            final BehaviourContextRunListener behaviourContextRunListener) {
        checkNotNull(behaviourContextClass, behaviourContextRunListener);
        try {
            runSpecification(behaviourContextClass);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    private void runSpecification(final BehaviourContextClass behaviourContextClass) {
        final SpecificationContext specificationContext = createSpecificationContext(behaviourContextClass);
        result.startTest(this);
        runSpecification(result, specificationContext);
        result.endTest(this);
    }

    private SpecificationContext createSpecificationContext(BehaviourContextClass behaviourContextClass) {
        final Method[] beforeSpecificationMethods = getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
        final SpecificationContext specificationContext = new SpecificationContextImpl(behaviourContextClass, beforeSpecificationMethods,
                afterSpecificationMethods, specificationMethod);
        return specificationContext;
    }

    @SuppressWarnings({"CatchGenericClass"})
    // DEBT IllegalCatch {
    private void runSpecification(final TestResult result, final SpecificationContext specificationContext) {
        try {
            specificationRunner.run(specificationContext);
        } catch (VerificationException e) {
            result.addFailure(this, new AssertionFailedError(e.getMessage()));
        } catch (Throwable e) {
            result.addError(this, e);
        }
    }
    // } DEBT IllegalCatch

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    private Method[] getMethods(final BehaviourContextClass behaviourContextClass, final Class<? extends Annotation> annotationType,
            final NamingConvention namingConvention) {
        return methodLocator.locateAll(behaviourContextClass.getClass(), annotationType, namingConvention);
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
}
