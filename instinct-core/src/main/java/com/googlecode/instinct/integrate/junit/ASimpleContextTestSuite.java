package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public final class ASimpleContextTestSuite extends TestSuite implements BehaviourContextRunListener, SpecificationRunListener {
    private TestSuite suite;

    public ASimpleContextTestSuite() {
        run(new BehaviourContextClassImpl(ASimpleContext.class));
    }

    public static Test suite() {
        return new ASimpleContextTestSuite();
    }

    private void run(final BehaviourContextClass behaviourContext) {
        setName(behaviourContext.getName());
        behaviourContext.run(this);
    }

    public void onBehaviourContext(final BehaviourContextClass behaviourContext) {
        suite = new TestSuite(behaviourContext.getName());
        addTest(suite);
    }

    public void onSpecification(final SpecificationMethod specification) {
        suite.addTest(new TestCase(specification.getName()) {
            @Override
            public void run(final TestResult result) {
                System.out.println("Running...!");
                super.run(result);
            }
        });
    }

    @BehaviourContext
    private final class ASimpleContext {
        @Specification
        public void toCheckVerification() {
            mustBeTrue(true);
        }
    }
}
