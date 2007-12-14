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

package com.googlecode.instinct.actor;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import org.jmock.api.ExpectationError;

// Note. Cannot use auto-wired mocks here as we're testing the mock creator.
public final class MockCreatorAtomicTest extends InstinctTestCase {
    @Subject(implementation = MockCreator.class) private SpecificationDoubleCreator mockCreator;

    public void testConformsToClassTraits() {
        checkClass(MockCreator.class, SpecificationDoubleCreator.class);
    }

    // Note. Checks side effects of jMock mocks :(
    public void testCreatesMocksForTypes() {
        final CharSequence sequence = mockCreator.createDouble(CharSequence.class, "a char sequence");
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                sequence.charAt(0);
            }
        });
    }

    public void testCreatesArraysFullOfMocks() {
        final CharSequence[] sequences = {};
        final CharSequence[] mockSequences = mockCreator.createDouble(sequences.getClass(), "some sequences");
        expect.that(mockSequences).isOfSize(3);
        for (final CharSequence mockSequence : mockSequences) {
            expectThatElementIsAMock(mockSequence);
        }
    }

    public void testWrapsLowerLevelExceptionsInSomethingMoreUsable() {
        final String message = "Unable to create a mock java.lang.String (with role name 'string'). Mock types cannot be final, you may want to " +
                "use a dummy or a stub.";
        expectException(SpecificationDoubleCreationException.class, message, new Runnable() {
            public void run() {
                mockCreator.createDouble(String.class, "string");
            }
        });
    }

    private void expectThatElementIsAMock(final CharSequence mockSequence) {
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                mockSequence.charAt(0);
            }
        });
    }
}