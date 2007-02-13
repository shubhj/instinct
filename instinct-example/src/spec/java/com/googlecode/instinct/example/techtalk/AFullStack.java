package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.verify.Verify.mustBeFalse;

@BehaviourContext
public final class AFullStack {
    private Stack stack;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }

    @Specification
    void mustNoLongerBeFullAfterPop() {
        stack.pop();
        mustBeFalse(stack.isEmpty());
    }
}
