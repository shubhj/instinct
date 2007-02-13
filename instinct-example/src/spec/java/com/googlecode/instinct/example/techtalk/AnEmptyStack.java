package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.verify.Verify;

@BehaviourContext
public final class AnEmptyStack {
    private Stack stack;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl();
    }

    @Specification
    void mustBeEmpty() {
        Verify.mustBeTrue(stack.isEmpty());
//            mustBeFalse(stack.isEmpty());
    }

    @Specification
    void mustNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        Verify.mustBeFalse(stack.isEmpty());
    }
}
