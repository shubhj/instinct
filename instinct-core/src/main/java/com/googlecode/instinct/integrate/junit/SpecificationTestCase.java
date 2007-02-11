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
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationContextImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.VerificationException;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

@Suggest({"Try and just use the interface Test rather than concrete extension.",
        "Could clean this up by using something like JDave's runners"})
@SuppressWarnings({"UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests", "JUnitTestCaseInProductSource"})
public final class SpecificationTestCase extends TestCase {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final Class<?> behaviourContextClass;
    private final Method specificationMethod;

    @Suggest({"Should probably only pass the methods in, not the context class"})
    @SuppressWarnings({"JUnitTestCaseWithNonTrivialConstructors"})
    public SpecificationTestCase(final Class<?> behaviourContextClass, final Method specificationMethod) {
        super(specificationMethod == null ? "" : specificationMethod.getName());
        checkNotNull(behaviourContextClass, specificationMethod);
        this.behaviourContextClass = behaviourContextClass;
        this.specificationMethod = specificationMethod;
    }

    @Override
    public void run(final TestResult result) {
        // In superclass, calls result.run() which calls runBare()
        checkNotNull(result);
        try {
            runSpecification(result);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    @Override
    public String getName() {
        return specificationMethod.getName();
    }

    @Override
    public String toString() {
        return specificationMethod.getName();
    }

    @Suggest("This contains heavy duplication with BehaviourContextRunnerImpl, figure out how to remove it")
    private void runSpecification(final TestResult result) {
        final Method[] beforeSpecificationMethods = getMethods(behaviourContextClass, BeforeSpecification.class,
                new BeforeSpecificationNamingConvention());
        final Method[] afterSpecificationMethods = getMethods(behaviourContextClass, AfterSpecification.class,
                new AfterSpecificationNamingConvention());
        final SpecificationContext specificationContext = new SpecificationContextImpl(behaviourContextClass, beforeSpecificationMethods,
                afterSpecificationMethods, specificationMethod);
        // Note. This is heavily influenced to the implementation of junit.framework.TestResult.run().
        result.startTest(this);
        runSpecification(result, specificationContext);
        result.endTest(this);
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
}
