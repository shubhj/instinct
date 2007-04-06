package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.expect.Expect.expect;

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
        expect.that(stack.isEmpty()).equalTo(true);
    }

    @Specification
    void mustNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        expect.that(stack.isEmpty()).equalTo(false);
    }

    @Specification
    void willReturnTheSameObjectWhenPushed() {
        stack.push(object);
        final Object o = stack.pop();
        expect.that(o).sameInstanceAs(object);
    }
}
