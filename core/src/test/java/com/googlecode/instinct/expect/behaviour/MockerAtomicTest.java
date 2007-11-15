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

package com.googlecode.instinct.expect.behaviour;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.getJMock2Mockery;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import static com.googlecode.instinct.expect.behaviour.Mocker.reset;
import static com.googlecode.instinct.expect.behaviour.Mocker.sequence;
import static com.googlecode.instinct.expect.behaviour.Mocker.verify;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import org.jmock.Expectations;
import org.jmock.api.ExpectationError;

public final class MockerAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(Mocker.class);
    }

    public void testHoldsTheSingleJMock2MockeryInstance() {
        expect.that(getJMock2Mockery()).sameInstanceAs(getJMock2Mockery());
        expect.that(getJMock2Mockery()).isNotNull();
    }

    public void testCreatesMocks() {
        final CharSequence chars = mock(CharSequence.class);
        expect.that(chars).isNotNull();
    }

    public void testCreatesMocksWithRoleNames() {
        final CharSequence chars = mock(CharSequence.class, "username");
        expect.that(chars).isNotNull();
    }

    public void testVerifiesMockCalls() {
        final CharSequence chars = mock(CharSequence.class);
        getJMock2Mockery().checking(new Expectations() {
            {
                one(chars).charAt(0);
            }
        });
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                verify();
            }
        });
        reset();
    }

    public void testCreatesSequences() {
        expect.that(sequence()).isNotNull();
    }
}
