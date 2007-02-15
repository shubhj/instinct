/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.example;

import com.googlecode.instinct.internal.util.Suggest;

@SuppressWarnings({"EmptyClass"})
@Suggest("Implement all features of: http://rspec.rubyforge.org/examples.html")
public final class ContextExample {
/*
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

        @Specification
        void mustNoLongerBeEmptyAfterPush() {
            stack.push(new Object());
            mustBeFalse(stack.isEmpty());
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
            mustBeFalse(stack.isEmpty());
        }
    }

*/
/* Still to implement:
context "An empty stack" do
  specify "should complain when sent 'peek'" do
    lambda { @stack.peek }.should_raise StackUnderflowError
  end

  specify "should complain when sent 'pop'" do
    lambda { @stack.pop }.should_raise StackUnderflowError
  end
end
*/
/*
*/
}

