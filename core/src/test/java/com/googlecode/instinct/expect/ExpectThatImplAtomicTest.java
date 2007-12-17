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
import com.googlecode.instinct.expect.behaviour.BehaviourExpectations;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import org.jmock.Expectations;

@Suggest(
        {"Behaviour expectations plan.", "1. Drive out that(Expectations) to support jMock 2", "2. Move Mocker12 & JMock12Mockery infrastructure over to jMock2. Fix tests as required.", "3. Use that() to drive out facade on top of ObjectChecker that delegates to either a state or", "mock aware checker depending on object status (i.e. is it a testdouble?)."})
public final class ExpectThatImplAtomicTest extends InstinctTestCase {
    private ExpectThat expectThat;
    private StateExpectations stateExpectations;
    private BehaviourExpectations behaviourExpectations;
    private Object object;
    private ObjectChecker<?> objectChecker;

    @Override
    public void setUpTestDoubles() {
        stateExpectations = mock(StateExpectations.class);
        behaviourExpectations = mock(BehaviourExpectations.class);
        object = mock(Object.class);
        objectChecker = mock(ObjectChecker.class);
    }

    @Override
    public void setUpSubject() {
        expectThat = createSubject(ExpectThatImpl.class, stateExpectations, behaviourExpectations);
    }

    public void testConformsToClassTraits() {
        checkClassWithoutParamChecks(ExpectThatImpl.class, ExpectThat.class);
    }

    @Suggest("Write a delgation checker to check this for all methods.")
    public void testObjectFormThatDelegatesToStateExpectationsObjectFormThat() {
        expect.that(new Expectations() {
            {
                one(stateExpectations).that(object);
                will(returnValue(objectChecker));
            }
        });
        assertSame(objectChecker, expectThat.that(object));
    }
}
