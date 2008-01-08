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

package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkAbstract;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;

public final class PrimordialAtomicTest extends InstinctTestCase {
    @Subject(implementation = LungFish.class) private Primordial lungFish;
    @Subject(implementation = Monkey.class) private Primordial monkey;

    public void testConformsToClassTraits() {
        checkAbstract(Primordial.class);
        checkPublic(Primordial.class);
    }

    public void testHasADumbButWorkableHashCodeThatCanBeOveriddenBySubclasses() {
        expect.that(lungFish.hashCode()).isEqualTo(42);
        expect.that(monkey.hashCode()).isEqualTo(50);
    }

    private static final class LungFish extends Primordial {
    }

    private static final class Monkey extends Primordial {
        @Override
        public int hashCode() {
            return 50;
        }
    }
}
