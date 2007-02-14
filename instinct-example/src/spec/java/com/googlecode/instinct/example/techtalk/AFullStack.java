package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.verify.Verify.mustBeFalse;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

@BehaviourContext
public final class AFullStack {
    private Stack<Integer> stack;
    private static final int SIZE = 10;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl<Integer>();
        for (int i = 0; i < SIZE; i++) {
            stack.push(i);
        }
    }

    @Specification
    void mustNoLongerBeFullAfterPop() {
        stack.pop();
        mustBeFalse(stack.isEmpty());
    }

    @Specification
    void isNoLongerFullAfterPoppingAllElements() {
        for (int i = 0; i < SIZE; i++) {
            stack.pop();
        }
        mustBeTrue(stack.isEmpty());
    }
}
