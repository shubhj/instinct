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
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;
import static java.util.Arrays.asList;
import java.util.Collection;

public final class IterableCheckerImplAtomicTest extends InstinctTestCase {
    private IterableChecker<String, Iterable<String>> checker;

    @Override
    public void setUpSubject() {
        final Collection<String> subjectCollection = asList("one", "two");
        checker = new IterableCheckerImpl<String, Iterable<String>>(subjectCollection);
    }

    public void testConformsToClassTraits() {
        checkPublic(IterableCheckerImpl.class);
    }

    public void testFindsAllItemsThatAreInSuppliedCollection() {
        checker.containsItems(asList("one", "two"));
    }
}