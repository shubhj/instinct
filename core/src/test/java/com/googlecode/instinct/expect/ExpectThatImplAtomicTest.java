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
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import org.jmock.Expectations;

@SuppressWarnings({"unchecked"})
public final class ExpectThatImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private StateExpectations expectThat;
    @Mock private StateExpectations stateExpectations;
    @Mock private BehaviourExpectations behaviourExpectations;
    @Dummy private Object object;
    @Dummy private Object objectChecker;

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
        expect.that(expectThat.that(object)).isTheSameInstanceAs((ObjectChecker<Object>) objectChecker);
    }
}
