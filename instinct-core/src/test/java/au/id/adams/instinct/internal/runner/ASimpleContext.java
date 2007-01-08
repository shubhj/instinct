package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.core.annotate.BeforeSpecification;
import au.id.adams.instinct.core.annotate.AfterSpecification;
import static au.id.adams.instinct.verify.Verify.mustBeTrue;

@BehaviourContext
public final class ASimpleContext {

    @BeforeSpecification
    public void setUp() {
        System.out.println("Foo");
    }

    @BeforeSpecification
    public void setUpAgain() {
        System.out.println("Fooby");
    }

    @Specification
    public void toCheckVerification() {
        System.out.println("Bar");
        mustBeTrue(true);
    }

    @AfterSpecification
    public void tearDown() {
        System.out.println("Baz");
    }

    @AfterSpecification
    public void tearDownAgain() {
        System.out.println("Qux");
    }
}
