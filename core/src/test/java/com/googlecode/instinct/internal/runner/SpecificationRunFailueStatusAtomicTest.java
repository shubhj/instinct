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

package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.Option;

@SuppressWarnings({"UnusedDeclaration"})
public final class SpecificationRunFailueStatusAtomicTest extends InstinctTestCase {
    @Stub private SpecificationFailureException error;

    public void testConformsToClassTraits() {
        checkClass(SpecificationRunFailureStatus.class, SpecificationRunStatus.class);
    }

    public void testGetDetailedStatusReturnsError() {
        final SpecificationRunStatus runStatus = new SpecificationRunFailureStatus(error, Option.<Throwable>none());
        expect.that(runStatus.getDetails()).isTheSameInstanceAs(error);
    }
}
