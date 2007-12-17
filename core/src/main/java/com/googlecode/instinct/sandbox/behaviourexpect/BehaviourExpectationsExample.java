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

package com.googlecode.instinct.sandbox.behaviourexpect;

import static com.googlecode.instinct.sandbox.behaviourexpect.BehaviourExpect.expect;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;

// Note. This doesn't actually work, it's just trying to get the correct typing going to see what's possible.

// SUPPRESS Indentation {
@SuppressWarnings({"AccessStaticViaInstance", "EmptyClass"})
public final class BehaviourExpectationsExample {
    private final List<String> stringList = new ArrayList<String>();
    private final SomeClass someClass = new SomeClass(stringList);

    void playingWithBehaviourExpectations() {

        // Option 1 - jMock 1 fallback
//        expect.that(stringList, once()).method("add").with(eq("abc")).will(returnValue(true));
//        expect.that(stringList).method("clear");
          // see ... insert URL to email here.       
//        expect.that().method("add").isCalledOn(stringList).andReturns(true);
//        expect.that().method("clear").isCalledOn(stringList);

        // Option 2 - DSL w/ method completion
//        expect.that(one(stringList).add("abc")).will(returnValue(true));
//        expect.that().one(stringList).clear();

        // Option 3 - jMock 2 style
        expect.that(new Expectations() {
            {
                one(stringList).add("abc"); will(returnValue(true));
                one(stringList).clear();
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
