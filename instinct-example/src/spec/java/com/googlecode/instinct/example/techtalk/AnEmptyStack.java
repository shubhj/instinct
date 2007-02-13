package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.verify.Verify.mustBeFalse;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

@BehaviourContext
public final class AnEmptyStack {
    private Stack stack;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl();
    }

    @Specification
    void mustBeEmpty() {
        mustBeTrue(stack.isEmpty());
    }

    @Specification
    void mustNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        mustBeFalse(stack.isEmpty());
    }
}
