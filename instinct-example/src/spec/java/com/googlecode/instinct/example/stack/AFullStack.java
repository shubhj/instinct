package com.googlecode.instinct.example.stack;

import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.Suggest;

@BehaviourContext
public final class AFullStack {
    private static final int SIZE = 10;
    private Stack<Integer> stack;

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
