package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;

@Context
public final class AnEmptyStack {
    @Subject
    private Stack<Object> stack;
    @Dummy
    private Object object;

    @BeforeSpecification
    void setUp() {
        object = new Object();
        stack = new StackImpl<Object>();
    }

    @Specification
    void mustBeEmpty() {
        expect.that(stack.isEmpty()).isTrue();
    }

    @Specification
    void mustNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification
    void willReturnTheSameObjectWhenPushed() {
        stack.push(object);
        final Object o = stack.pop();
        expect.that(o).sameInstanceAs(object);
    }
}
