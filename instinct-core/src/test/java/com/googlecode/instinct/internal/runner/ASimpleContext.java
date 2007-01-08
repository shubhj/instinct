package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.AfterSpecification;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

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
