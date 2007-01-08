package au.id.adams.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.internal.aggregate.locate.AnnotatedMethodLocator;
import au.id.adams.instinct.internal.aggregate.locate.AnnotatedMethodLocatorImpl;
import au.id.adams.instinct.internal.runner.BehaviourContextRunner;
import au.id.adams.instinct.internal.runner.BehaviourContextRunnerImpl;
import au.id.adams.instinct.internal.util.Suggest;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import static au.id.adams.instinct.internal.util.Suggest.Priority.LOW;
import au.net.netstorm.boost.edge.EdgeException;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public final class BehaviourContextTestCase implements Test {
    private AnnotatedMethodLocator methodLocator = new AnnotatedMethodLocatorImpl();
    private BehaviourContextRunner contextRunner = new BehaviourContextRunnerImpl();
    private final Class<?> specificationClass;

    public <T> BehaviourContextTestCase(final Class<T> specificationClass) {
        checkNotNull(specificationClass);
        this.specificationClass = specificationClass;
    }

    public int countTestCases() {
        return getNumberOfSpecificationMethods();
    }

    public void run(final TestResult result) {
        checkNotNull(result);
        try {
            runTest(result);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    @Override
    public String toString() {
        return specificationClass.getName();
    }

    // Note. This is heavily influenced to the implementation of junit.framework.TestResult.run().
    private void runTest(final TestResult result) {
        result.startTest(this);
        result.runProtected(this, new ContextProtectable(contextRunner, specificationClass));
        result.endTest(this);
    }

    @SuppressWarnings({"ProhibitedExceptionThrown"})
    private void handleException(final EdgeException e) {
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }

    @Suggest(value = "Should we cache this?", priority = LOW)
    private int getNumberOfSpecificationMethods() {
        // This will break if we allow different ways to specify specifications
        final Method[] methods = methodLocator.locate(specificationClass, Specification.class);
        return methods.length;
    }

    private static class ContextProtectable implements Protectable {
        private final BehaviourContextRunner contextRunner;
        private final Class<?> specificationClass;

        private <T> ContextProtectable(final BehaviourContextRunner contextRunner, final Class<T> specificationClass) {
            this.contextRunner = contextRunner;
            this.specificationClass = specificationClass;
        }

        public void protect() {
            contextRunner.run(specificationClass);
        }
    }
}
