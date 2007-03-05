package com.googlecode.instinct.behaviourexpect;

import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.behaviourexpect.BehaviourExpect.expect;
import static com.googlecode.instinct.behaviourexpect.BehaviourExpect.one;
import static com.googlecode.instinct.behaviourexpect.BehaviourExpect.returnValue;
import static com.googlecode.instinct.behaviourexpect.BehaviourExpect.will;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

// DEBT Indentation {
@BehaviourContext
public final class BehaviourExpectationsContext {
    private final List<String> strings = new ArrayList<String>();
    private final SomeClass someClass = new SomeClass(strings);

    @Specification
    void playingWithBehaviourExpectations() {

        // Option 1 - jMock 1 fallback
        expect.that().call(strings).method("abc").will(returnValue(true));

        // Option 2 - DSL w/ method completion
        expect.that(one(strings).add("abc")).will(returnValue(true));
        expect.that().one(strings).clear();

        // Option 3 - jMock 2 style
        expect.that(new Expectations() { {
            one(strings).add("abc"); will(returnValue('E'));
            one(strings).clear();
        }});

        someClass.doStuff();
    }

    private static final class SomeClass {
        private final List<String> strings;

        private SomeClass(final List<String> strings) {
            this.strings = strings;
        }

        void doStuff() {
        }
    }
}
// } DEBT Indentation
