package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class AnEmptyStack {
    @Subject private Stack<Object> stack;
    @Dummy private Object object;

    @BeforeSpecification
    void before() {
        object = new Object();
        stack = new StackImpl<Object>();
    }

    @Specification
    void isEmpty() {
        expect.that(stack.isEmpty()).isTrue();
    }

    @Specification
    void isNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification
    void returnTheSameObjectWhenPushed() {
        stack.push(object);
        final Object o = stack.pop();
        expect.that(o).sameInstanceAs(object);
    }

    @Specification(expectedException = IllegalStateException.class, withMessage = "Cannot pop an empty stack")
    void throwsExceptionWhenPoppedWithNoElements() {
        stack.pop();
    }

    @Specification(state = PENDING)
    void hasSomeNewFeatureWeHaveNotThoughtOfYet() {
        expect.that(true).isFalse();
    }
}
