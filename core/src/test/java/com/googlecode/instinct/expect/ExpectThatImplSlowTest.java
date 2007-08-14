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

package com.googlecode.instinct.expect;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.getJMock2Mockery;
import com.googlecode.instinct.internal.expect.behaviour.JMock2Mockery;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Expectations;

@SuppressWarnings({"EmptyClass"})
public final class ExpectThatImplSlowTest extends InstinctTestCase {
    private CharSequence charSequence;
    private JMock2Mockery mockery;

    @Suggest("Don't use the mocker directly, use the Mocker. Requires methods to be exposed.")
    @Override
    public void setUpTestDoubles() {
        mockery = getJMock2Mockery();
        charSequence = mockery.mock(CharSequence.class);
    }

    @Override
    public void tearDown() {
        mockery.verify();
    }

    public void testSetsExpectationsUsingJMock2Syntax() {
        expect.that(new Expectations() {
            {
                one(charSequence).charAt(0);
            }
        });
        charSequence.charAt(0);
    }
}
