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
    }

    @BeforeSpecification
    public void setUpAgain() {
    }

    @Specification
    public void toCheckVerification() {
        mustBeTrue(true);
    }

    @AfterSpecification
    public void tearDown() {
    }

    @AfterSpecification
    public void tearDownAgain() {
    }
}
