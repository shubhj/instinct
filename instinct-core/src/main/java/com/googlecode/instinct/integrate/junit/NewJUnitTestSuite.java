package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.VerificationException;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

@Suggest("Rename to BehaviourContextTestSuite")
public final class NewJUnitTestSuite extends TestSuite implements BehaviourContextRunStrategy, SpecificationRunStrategy {
    @Suggest("Do we need to make this a field? Does it need to be shared to make JUnit integration work?")
    private TestSuite currentSuite;

    public <T> NewJUnitTestSuite(final Class<T> behaviourContextType) {
        run(new BehaviourContextClassImpl(behaviourContextType));
    }

    @Suggest("Remove once finished experimenting.")
    public static Test suite() {
        final TestSuite suite = new NewJUnitTestSuite(ASimpleContext.class);
        suite.setName("Instinct JUnit Integration");
        return suite;
    }

    private void run(final BehaviourContextClass behaviourContext) {
        setName(behaviourContext.getName());
        behaviourContext.run(this, this);
    }

    @Suggest("To make contexts and specs symmetric, we should do more here..., rather than doing the work in the BehaviourContextRunner")
    public BehaviourContextResult onBehaviourContext(final BehaviourContextClass behaviourContext) {
        currentSuite = new TestSuite(behaviourContext.getName());
        addTest(currentSuite);
        return null;
    }

    public SpecificationResult onSpecification(final SpecificationMethod specificationMethod) {
        currentSuite.addTest(new TestCase(specificationMethod.getName()) {
            private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
            private TestResult result;

            @Override
            public void run(final TestResult result) {
                this.result = result;
                super.run(result);
            }

            @Override
            public void runBare() {
                try {
                    runSpecification(result, specificationMethod.getSpecificationContext());
                } catch (EdgeException e) {
                    handleException(e);
                }
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

            @Suggest("Do we need to do this elsewhere in the JUnit integration? Method finding, etc.?")
            @SuppressWarnings({"ProhibitedExceptionThrown"})
            private void handleException(final EdgeException e) {
                // Note. Need to dig down as reflection is pushed behind an edge.
                if (e.getCause() instanceof InvocationTargetException) {
                    throw (RuntimeException) e.getCause().getCause();
                } else {
                    throw e;
                }
            }
        });
        return null;
    }

    @Suggest("Remove, once experimenting is done")
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
