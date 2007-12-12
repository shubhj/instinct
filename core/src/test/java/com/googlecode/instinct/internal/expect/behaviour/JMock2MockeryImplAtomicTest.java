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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.internal.ExpectationBuilder;
import org.jmock.lib.legacy.ClassImposteriser;

@SuppressWarnings({"JUnitTestCaseWithNonTrivialConstructors", "EmptyClass"})
public final class JMock2MockeryImplAtomicTest extends InstinctTestCase {
    private Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    @Subject private JMock2Mockery jMock2Mockery;
    @Stub private Class<CharSequence> typeToMock;
    @Stub private String returnedMock;
    @Stub private String roleName;
    @Stub private String stateName;
    @Dummy private States state;
    @Dummy private Sequence sequence;
    @Dummy private ExpectationBuilder expectations;
    // Note. We can't use automocking here as we're testing the mocking infrastructure. Need to use raw jMock instead.
    private Mockery mockery;

    @Override
    public void setUpTestDoubles() {
        mockery = context.mock(Mockery.class);
    }

    @Override
    public void setUpSubject() {
        jMock2Mockery = createSubject(JMock2MockeryImpl.class, mockery);
    }

    @Override
    public void tearDown() {
        context.assertIsSatisfied();
    }

    public void testConformsToClassTraits() {
        checkClass(JMock2MockeryImpl.class, JMock2Mockery.class);
    }

    public void testDelegatesMockCallsToUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).mock(typeToMock);
                will(returnValue(returnedMock));
            }
        });
        expect.that(jMock2Mockery.mock(typeToMock)).isTheSameInstanceAs(returnedMock);
    }

    public void testDelegatesMockWithRoleNameCallsToUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).mock(typeToMock, roleName);
                will(returnValue(returnedMock));
            }
        });
        expect.that(jMock2Mockery.mock(typeToMock, roleName)).isTheSameInstanceAs(returnedMock);
    }

    public void testDelegatesCheckingToUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).checking(expectations);
            }
        });
        jMock2Mockery.checking(expectations);
    }

    public void testVerifiesByCallingAssertIsSatisfiedOnUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).assertIsSatisfied();
            }
        });
        jMock2Mockery.verify();
    }

    public void testDelegatesSequenceCallToUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).sequence("Sequence-0"); will(returnValue(sequence));
            }
        });
        expect.that(jMock2Mockery.sequence()).isTheSameInstanceAs(sequence);
    }

    public void testDelegatesStatesCallToUnderlyingMockery() {
        context.checking(new Expectations() {
            {
                one(mockery).states(stateName); will(returnValue(state));
            }
        });
        expect.that(jMock2Mockery.states(stateName)).isTheSameInstanceAs(state);
    }
}
