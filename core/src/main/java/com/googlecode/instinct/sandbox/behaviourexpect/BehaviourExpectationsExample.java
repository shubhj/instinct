package com.googlecode.instinct.sandbox.behaviourexpect;

import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.eq;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.expect;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.once;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.one;
import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.returnValue;
import java.util.ArrayList;
import java.util.List;
import org.jmock.Expectations;

// Note. This doesn't actually work, it's just trying to get the correct typing going to see what's possible.

// SUPPRESS Indentation {
@SuppressWarnings({"AccessStaticViaInstance", "EmptyClass"})
@Context
public final class BehaviourExpectationsExample {
    private final List<String> stringList = new ArrayList<String>();
    private final SomeClass someClass = new SomeClass(stringList);

    @Specification
    void playingWithBehaviourExpectations() {

        // Option 1 - jMock 1 fallback
        expect.that(stringList, once()).method("add").with(eq("abc")).will(returnValue(true));
        expect.that(stringList).method("clear");

        // Option 2 - DSL w/ method completion
        expect.that(one(stringList).add("abc")).will(returnValue(true));
        expect.that().one(stringList).clear();

        // Option 3 - jMock 2 style
        expect.that(new Expectations() {{
            one(stringList).add("abc"); will(returnValue(true));
            one(stringList).clear();
        }});
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
