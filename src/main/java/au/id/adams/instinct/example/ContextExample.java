package au.id.adams.instinct.example;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.BeforeSpecification;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.internal.util.Suggest;
import static au.id.adams.instinct.verify.Verify.mustBeFalse;
import static au.id.adams.instinct.verify.Verify.mustBeTrue;

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
        void shouldBeEmpty() {
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
        void shouldNoLongerBeFullAfterPop() {
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

