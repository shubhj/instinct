package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

@Suggest("Rename to testcase")
public final class XxxBehaviourContextTestSuite extends TestCase implements Test {
    //    private final SpecificationRunner contextRunner = new BehaviourContextRunnerImpl();
    private final Method specificationMethod;

    public XxxBehaviourContextTestSuite(final Method specificationMethod) {
        checkNotNull(specificationMethod);
        this.specificationMethod = specificationMethod;
    }

    public void run(final TestResult result) {
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

    // Note. This is heavily influenced to the implementation of junit.framework.TestResult.run().
    private void runSpecification(final TestResult result) {
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
