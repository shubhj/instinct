package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.verify.Verify;
import static com.googlecode.instinct.verify.Verify.mustBeFalse;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

@BehaviourContext
public final class AnEmptyStack {
    private Stack<Object> stack;
    private Object object;

    @BeforeSpecification
    void setUp() {
        object = new Object();
        stack = new StackImpl<Object>();
    }

    @Specification
    void mustBeEmpty() {
        mustBeTrue(stack.isEmpty());
    }

    @Specification
    void mustNoLongerBeEmptyAfterPush() {
        stack.push(object);
        mustBeFalse(stack.isEmpty());
    }

    @Specification
    void willReturnTheSameObjectWhenPushed() {
        stack.push(object);
        final Object o = stack.pop();
        Verify.mustBeTrue(o == object);
    }
}
