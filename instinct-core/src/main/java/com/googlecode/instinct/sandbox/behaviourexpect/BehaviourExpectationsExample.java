package com.googlecode.instinct.sandbox.behaviourexpect;

import java.util.ArrayList;
import java.util.List;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.eq;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.expect;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.once;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.one;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.returnValue;
import org.jmock.Expectations;

// SUPPRESS Indentation {
@SuppressWarnings({"AccessStaticViaInstance", "EmptyClass"})
@BehaviourContext
public final class BehaviourExpectationsExample {
    private final List<String> strings = new ArrayList<String>();
    private final SomeClass someClass = new SomeClass(strings);

    @Specification
    void playingWithBehaviourExpectations() {

        // Option 1 - jMock 1 fallback
        expect.that(strings, once()).method("add").with(eq("abc")).will(returnValue(true));
        expect.that(strings).method("clear");

        // Option 2 - DSL w/ method completion
        expect.that(one(strings).add("abc")).will(returnValue(true));
        expect.that().one(strings).clear();

        // Option 3 - jMock 2 style
        expect.that(new Expectations() {
            {
                one(strings).add("abc");
                will(returnValue(true));
                one(strings).clear();
            }
        });

        someClass.doStuff();
    }

    private static final class SomeClass {
        private final List<String> strings;

        private SomeClass(final List<String> strings) {
            this.strings = strings;
        }

        void doStuff() {
            strings.add("abc");
            strings.clear();
        }
    }
}
// } SUPPRESS Indentation
