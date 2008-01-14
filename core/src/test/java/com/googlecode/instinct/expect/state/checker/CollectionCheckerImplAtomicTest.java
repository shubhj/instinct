/*
 * Copyright 2006-2008 Workingmouse
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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;
import static java.util.Arrays.asList;

public final class CollectionCheckerImplAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkPublic(CollectionCheckerImpl.class);
    }

    public void testHasTheSameContentIsSymmetric() {
        expect.that(asList(1, 2, 3, 4)).hasTheSameContentAs(4, 3, 2, 1);
        expect.that(asList(4, 3, 2, 1)).hasTheSameContentAs(1, 2, 3, 4);
    }

    public void testHasTheSameContentAsDoesNotCareAboutOrdering() {
        expect.that(asList(1, 2, 3, 4)).hasTheSameContentAs(4, 3, 2, 1);
    }

    public void testHasTheSameContentAsFailsIfElementsRepeated() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                expect.that(asList(1, 2, 3, 4)).hasTheSameContentAs(1, 1, 4, 3);
            }
        });
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                expect.that(asList(1, 2, 4, 3)).hasTheSameContentAs(1, 1, 3, 4);
            }
        });
    }

    public void testHasTheSameContentAsFails() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                expect.that(asList(1, 2, 4, 3)).hasTheSameContentAs(1, 1, 3, 4);
            }
        });
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                expect.that(asList(1, 1, 3, 4)).hasTheSameContentAs(1, 2, 3, 4);
            }
        });
    }

    public void testDifferentSizeListsDoNotContainTheSameContent() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                expect.that(asList(1, 2, 3, 4)).hasTheSameContentAs(1, 2, 3);
            }
        });
    }
}
