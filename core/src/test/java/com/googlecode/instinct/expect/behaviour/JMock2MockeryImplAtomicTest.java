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

import static com.googlecode.instinct.expect.Mocker12.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;
import org.jmock.Mockery;

public final class JMock2MockeryImplAtomicTest extends InstinctTestCase {
    private JMock2Mockery jMock2Mockery;
    private Mockery mockery;

    @Override
    public void setUpTestDoubles() {
        mockery = mock(Mockery.class);
    }

    @Override
    public void setUpSubject() {
        createSubject(JMock2MockeryImpl.class, mockery);
    }

    public void testConformsToClassTraits() {
        checkClass(JMock2MockeryImpl.class, JMock2Mockery.class);
    }
}
