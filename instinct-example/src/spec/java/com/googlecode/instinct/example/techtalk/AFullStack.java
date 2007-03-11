package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.Suggest;

@BehaviourContext
public final class AFullStack {
    private Stack<Integer> stack;
    private static final int SIZE = 10;

    @BeforeSpecification
    void fillUpStack() {
        stack = new StackImpl<Integer>();
        for (int i = 0; i < SIZE; i++) {
            stack.push(i);
        }
    }

    @Suggest("This is bogus, need an isFull() method.")
    @Specification
    void mustNoLongerBeFullAfterPop() {
        stack.pop();
        expect.that(stack.isEmpty()).equalTo(false);
    }

    @Specification
    void isNoLongerFullAfterPoppingAllElements() {
        for (int i = 0; i < SIZE; i++) {
            stack.pop();
        }
        expect.that(stack.isEmpty()).equalTo(true);
    }
}
