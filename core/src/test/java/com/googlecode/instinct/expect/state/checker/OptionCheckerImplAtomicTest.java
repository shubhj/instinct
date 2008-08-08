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

import static com.googlecode.instinct.expect.state.matcher.ToStringableOption.toStringableOption;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;
import fj.data.Option;
import static fj.data.Option.some;

public final class OptionCheckerImplAtomicTest extends InstinctTestCase {
    private OptionChecker<Integer> checkerWithNothing;
    private OptionChecker<Integer> checkerWithSomething;

    public void testConformsToClassTraits() {
        checkPublic(OptionCheckerImpl.class);
    }

    @Override
    public void setUpSubject() {
        checkerWithNothing = new OptionCheckerImpl<Integer>(toStringableOption(Option.<Integer>none()));
        checkerWithSomething = new OptionCheckerImpl<Integer>(toStringableOption(some(3)));
    }

    public void testsFailsIfIsSomeAndChecksIfIsNone() {
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                checkerWithSomething.isNone();
            }
        });
        expectException(AssertionError.class, new Runnable() {
            public void run() {
                checkerWithNothing.isSome();
            }
        });
    }

    public void testsPassesIfIsNoneAndIsNone() {
        checkerWithNothing.isNone();
        checkerWithSomething.isSome();
    }
}
