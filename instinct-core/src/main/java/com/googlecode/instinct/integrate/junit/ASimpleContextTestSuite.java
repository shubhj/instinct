package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

@Suggest("Rename, push class out as constructor parameter.")
public final class ASimpleContextTestSuite extends TestSuite implements BehaviourContextRunStrategy, SpecificationRunStrategy {
    private TestSuite suite;

    public ASimpleContextTestSuite() {
        run(new BehaviourContextClassImpl(ASimpleContext.class));
    }

    public static Test suite() {
        return new ASimpleContextTestSuite();
    }

    private void run(final BehaviourContextClass behaviourContext) {
        setName(behaviourContext.getName());
        behaviourContext.run(this, this);
    }

    @Suggest("To make contexts and specs symmetric, we should do more here..., rather than doing the work in the BehaviourContextRunner")
    public BehaviourContextResult onBehaviourContext(final BehaviourContextClass behaviourContext) {
        System.out.println("behaviourContext = " + behaviourContext);
        suite = new TestSuite(behaviourContext.getName());
        addTest(suite);
        return null;
    }

    public SpecificationResult onSpecification(final SpecificationMethod specification) {
        System.out.println("specification = " + specification);
        suite.addTest(new TestCase(specification.getName()) {
            @Override
            public void run(final TestResult result) {
                System.out.println("Running...!");
                super.run(result);
            }
        });
        return null;
    }

//    @SuppressWarnings({"CatchGenericClass"})
    // DEBT IllegalCatch {
//    private void runSpecification(final TestResult result, final SpecificationContext specificationContext) {
//        try {
//            specificationRunner.run(specificationContext);
//        } catch (VerificationException e) {
//            result.addFailure(this, new AssertionFailedError(e.getMessage()));
//        } catch (Throwable e) {
//            result.addError(this, e);
//        }
//    }
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

    @BehaviourContext
    public static final class ASimpleContext {
        @Specification
        public void toCheckVerification() {
            mustBeTrue(true);
        }

        @Specification
        public void toCheckVerificationAgain() {
            mustBeTrue(true);
        }
    }
}
