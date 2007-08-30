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

package com.googlecode.instinct.marker.naming;

import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.expect.Expect.expect;

public final class DummyNamingConventionAtomicTest extends InstinctTestCase {
    @Subject(implementation = DummyNamingConvention.class) private NamingConvention namingConvention;

    public void testConformsToClassTraits() {
        checkClass(DummyNamingConvention.class, NamingConvention.class);
    }

    public void testGetPattern() {
        expect.that(namingConvention.getPattern()).equalTo("^dummy");
    }
}
