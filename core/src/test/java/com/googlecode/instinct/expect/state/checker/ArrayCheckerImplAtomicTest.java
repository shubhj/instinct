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

package com.googlecode.instinct.expect.state.checker;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;

public final class ArrayCheckerImplAtomicTest extends InstinctTestCase {
    private ArrayChecker<String> checker;

    public void testConformsToClassTraits() {
        checkPublic(ArrayCheckerImpl.class);
    }

    @Override
    public void setUpSubject() {
        final String[] subjectArray = {"hello", "world"};
        checker = new ArrayCheckerImpl<String>(subjectArray);
    }

    public void testFindsItemsThatAreContainedWithinSubjectArray() {
        checker.containsItem("hello");
        checker.containsItem("world");
    }

    public void testThrowsExceptionWhenItemIsNotContainedInSubjectArray() {
        checkExceptionIsThrownWhenContainItemIsCalled(checker, "foo");
    }

    public void testDoesNotThrowExceptionWhenNullIsContainedWithSubjectArray() {
        final String[] subjectArrayThatContainsNull = {null, "bar"};
        final ContainerChecker<String> nullChecker = new ArrayCheckerImpl<String>(subjectArrayThatContainsNull);
        nullChecker.containsItem((String) null);
    }

    public void testThrowsExceptionWhenContainsItemIsCalledOnANullSubjectArray() {
        final String[] nullSubjectArray = null;
        final ArrayChecker<String> nullChecker = new ArrayCheckerImpl<String>(nullSubjectArray);
        checkExceptionIsThrownWhenContainItemIsCalled(nullChecker, null);
    }

    public void testWillNotFindItemsThatAreNotInSubjectArray() {
        checker.doesNotContainItem("foo");
    }

    public void testThrowsExceptionWhenUnexpectedItemsAreFoundInSubjectArray() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                checker.doesNotContainItem("hello");
            }
        });
    }

    private void checkExceptionIsThrownWhenContainItemIsCalled(final ArrayChecker<String> checker, final String searchString) {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                checker.containsItem(searchString);
            }
        });
    }
}
