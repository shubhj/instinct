package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.core.annotate.BeforeTest;
import au.id.adams.instinct.core.annotate.AfterTest;
import static au.id.adams.instinct.verify.Verify.mustBeTrue;

@BehaviourContext
public final class ASimpleContext {

    @BeforeTest
    public void setUp() {
        System.out.println("Foo");
    }

    @BeforeTest
    public void setUpAgain() {
        System.out.println("Fooby");
    }

    @Specification
    public void toCheckVerification() {
        System.out.println("Bar");
        mustBeTrue(true);
    }

    @AfterTest
    public void tearDown() {
        System.out.println("Baz");
    }

    @AfterTest
    public void tearDownAgain() {
        System.out.println("Qux");
    }
}
