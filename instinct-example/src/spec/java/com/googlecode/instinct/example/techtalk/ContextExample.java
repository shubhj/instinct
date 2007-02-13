package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.example.Stack;
import com.googlecode.instinct.example.StackImpl;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.Verify;

@SuppressWarnings({"EmptyClass"})
public final class ContextExample {
    @BehaviourContext
    public static final class AnEmptyStack {
        @Suggest("Label this a @Fixture, and remove use of new in setUp")
        private Stack stack;

        @BeforeSpecification
        void setUp() {
            stack = new StackImpl();
        }

        @Specification
        void mustBeEmpty() {
            Verify.mustBeTrue(stack.isEmpty());
        }

        @Specification
        void mustNoLongerBeEmptyAfterPush() {
            stack.push(new Object());
            Verify.mustBeFalse(stack.isEmpty());
        }
    }

    @BehaviourContext
    public static final class AFullStack {
        @Suggest("Label this a @Fixture, and remove use of new in setUp")
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
            Verify.mustBeFalse(stack.isEmpty());
        }
    }
}
