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

package com.googlecode.instinct.sandbox.behaviourexpect.behaviour.jmock1;

import com.googlecode.instinct.sandbox.behaviourexpect.MethodInvocationMatcher;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;

public interface JMock1Expectations {
    <T> MethodInvocationMatcher that(T mockedObject);

    <T> MethodInvocationMatcher that(T mockedObject, InvocationMatcher numberOfTimes);

    IdentityBuilder will(final Stub stubAction);

    Stub returnValue(Object returnValue);

    Stub throwException(Throwable throwable);

    InvocationMatcher once();

    InvocationMatcher times(int expectedNumberOfCalls);

//    InvocationMatcher times(int minNumberOfCalls, int maxNumberOfCalls);

    InvocationMatcher atLeastOnce();

    InvocationMatcher anyTimes();

    Constraint same(Object argument);

    Constraint anything();

    Constraint eq(Object argument);

    Constraint sameElements(Object[] argument);
}
