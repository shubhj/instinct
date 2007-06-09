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

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;
import static com.googlecode.instinct.expect.Expect.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.internal.ExpectationBuilder;
import org.jmock.lib.legacy.ClassImposteriser;

@SuppressWarnings({"JUnitTestCaseWithNonTrivialConstructors", "EmptyClass"})
public final class JMock2MockeryImplAtomicTest extends InstinctTestCase {
    private Mockery context = new Mockery(){{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    private JMock2Mockery jMock2Mockery;
    private ExpectationBuilder expectations;
    private Class<CharSequence> typeToMock;
    private String returnedMock;
    private String roleName;
    private Mockery mockery;

    @Override
    public void setUpTestDoubles() {
        mockery = context.mock(Mockery.class);
        expectations = context.mock(Expectations.class);
        typeToMock = CharSequence.class;
        returnedMock = "a mocked string";
        roleName = "roleName";
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
        context.checking(new Expectations() {{
            one(mockery).mock(typeToMock); will(returnValue(returnedMock));
        }});
        expect.that(jMock2Mockery.mock(typeToMock)).sameInstanceAs(returnedMock);
    }

    public void testDelegatesMockWithRoleNameCallsToUnderlyingMockery() {
        context.checking(new Expectations() {{
            one(mockery).mock(typeToMock, roleName); will(returnValue(returnedMock));
        }});
        expect.that(jMock2Mockery.mock(typeToMock, roleName)).sameInstanceAs(returnedMock);
    }

    public void testDelegatesCheckingToUnderlyingMockery() {
        context.checking(new Expectations(){{
            one(mockery).checking(expectations);
        }});
        jMock2Mockery.checking(expectations);
    }
}
