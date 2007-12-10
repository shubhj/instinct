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

package com.googlecode.instinct.internal.expect.behaviour;

import java.io.BufferedReader;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Expectations;

@SuppressWarnings({"EmptyClass"})
public final class JMock2MockeryImplSlowTest extends InstinctTestCase {
    private JMock2Mockery mockery;

    @Override
    public void setUpSubject() {
        mockery = new JMock2MockeryImpl();
    }

    @Override
    public void tearDown() {
        mockery.verify();
    }

    public void testMocksInterfaces() {
        final CharSequence sequence = mockery.mock(CharSequence.class);
        mockery.checking(new Expectations() {
            {
                one(sequence).charAt(0);
                will(returnValue('2'));
            }
        });
        expect.that(sequence.charAt(0)).isEqualTo('2');
    }

    public void testMocksConcreteClasses() {
        final BufferedReader reader = mockery.mock(BufferedReader.class);
        mockery.checking(new Expectations() {
            {
                one(reader).markSupported();
                will(returnValue(false));
            }
        });
        expect.that(reader.markSupported()).isEqualTo(false);
    }
}
