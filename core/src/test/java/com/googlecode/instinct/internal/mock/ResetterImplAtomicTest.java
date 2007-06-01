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

package com.googlecode.instinct.internal.mock;

import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ResetterImplAtomicTest extends InstinctTestCase {
    private Resetter resetter;
    private Resetable resetable1;
    private Resetable resetable2;

    @Override
    public void setUpTestDoubles() {
        resetable1 = mock(Resetable.class);
        resetable2 = mock(Resetable.class);
    }

    @Override
    public void setUpSubject() {
        resetter = new ResetterImpl();
    }

    public void testConformsToClassTraits() {
        checkClass(ResetterImpl.class, Resetter.class);
    }

    public void testResetsAllResetables() {
        expects(resetable1).method("reset");
        expects(resetable2).method("reset");
        resetter.addResetable(resetable1);
        resetter.addResetable(resetable2);
        resetter.reset();
    }
}
