package com.googlecode.instinct.example;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.verify.Verify.mustBeFalse;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

@SuppressWarnings({"EmptyClass"})
@Suggest("Implement all features of: http://rspec.rubyforge.org/examples.html")
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
            mustBeTrue(stack.isEmpty());
        }
    }

// Goal:
//    @BehaviourContext
//    final class AnEmptyStack {
//        @Fixture
//        private Stack stack;
//
//        @Specification
//        void shouldBeEmpty() {
//            mustBeTrue(stack.isEmpty());
//        }
//    }

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
            mustBeFalse(stack.isEmpty());
        }
    }

    /*
    context "An empty stack" do
  setup do
    @stack = Stack.new
  end

  specify "should be empty" do
    @stack.should_be_empty
  end

  specify "should no longer be empty after 'push'" do
    @stack.push "anything"
    @stack.should_not_be_empty
  end

  specify "should complain when sent 'peek'" do
    lambda { @stack.peek }.should_raise StackUnderflowError
  end

  specify "should complain when sent 'pop'" do
    lambda { @stack.pop }.should_raise StackUnderflowError
  end
end
     */
}

