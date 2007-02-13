package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;
import junit.framework.Test;
import junit.framework.TestSuite;

@Suggest({"Try and just use the interface Test rather than concrete extension."})
public final class BehaviourContextTestSuite extends TestSuite implements BehaviourContextRunStrategy, SpecificationRunStrategy {
    @Suggest("Do we need to make this a field? Does it need to be shared to make JUnit integration work?")
    private TestSuite currentContextSuite;

    @Suggest("Do we need to do run() in the constructor?")
    public <T> BehaviourContextTestSuite(final Class<T> behaviourContextType) {
        run(new BehaviourContextClassImpl(behaviourContextType));
    }

    @Suggest("Remove once finished experimenting.")
    public static Test suite() {
        final TestSuite suite = new BehaviourContextTestSuite(ASimpleContext.class);
        suite.setName("Instinct JUnit Integration");
        return suite;
    }

    private void run(final BehaviourContextClass behaviourContext) {
        setName(behaviourContext.getName());
        behaviourContext.run(this, this);
    }

    @Suggest("To make contexts and specs symmetric, we should do more here..., rather than doing the work in the BehaviourContextRunner")
    public BehaviourContextResult onBehaviourContext(final BehaviourContextClass behaviourContext) {
        currentContextSuite = new TestSuite(behaviourContext.getName());
        addTest(currentContextSuite);
        return null;
    }

    @Suggest("Do we need to do this callback business?")
    public SpecificationResult onSpecification(final SpecificationMethod specificationMethod) {
        currentContextSuite.addTest(new SpecificationTestCase(specificationMethod));
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
