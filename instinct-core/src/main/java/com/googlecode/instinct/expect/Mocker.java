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

import com.googlecode.instinct.internal.mock.Mockery;
import com.googlecode.instinct.internal.mock.MockeryImpl;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsInstanceOf;

@Suggest("This becomes the jMock 1.1 implementation of BehaviourExpectations.")
public final class Mocker {
    private static final Mockery MOCKERY = new MockeryImpl();

    private Mocker() {
        throw new UnsupportedOperationException();
    }

    public static <T> T mock(final Class<T> toMock) {
        return MOCKERY.mock(toMock);
    }

    public static <T> T mock(final Class<T> toMock, final String roleName) {
        return MOCKERY.mock(toMock, roleName);
    }

    public static NameMatchBuilder expects(final Object mockedObject) {
        return MOCKERY.expects(mockedObject);
    }

    public static NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        return MOCKERY.expects(mockedObject, expectation);
    }

    public static InvocationMatcher once() {
        return MOCKERY.once();
    }

    public static InvocationMatcher times(final int expectedNumberOfCalls) {
        return MOCKERY.times(expectedNumberOfCalls);
    }

//    public static InvocationMatcher times(final int minNumberOfCalls, final int maxNumberOfCalls) {
//        return MOCKERY.times(minNumberOfCalls, maxNumberOfCalls);
//    }

    public static InvocationMatcher atLeastOnce() {
        return MOCKERY.atLeastOnce();
    }

    public static InvocationMatcher anyTimes() {
        return MOCKERY.anyTimes();
    }

    public static Constraint anything() {
        return MOCKERY.anything();
    }

    public static <T> IsInstanceOf isA(final Class<T> operandClass) {
        return MOCKERY.isA(operandClass);
    }

    public static Constraint eq(final Object argument) {
        return MOCKERY.eq(argument);
    }

    public static Constraint same(final Object argument) {
        return MOCKERY.same(argument);
    }

    public static Constraint sameElements(final Object[] argument) {
        return MOCKERY.sameElements(argument);
    }

    public static Stub returnValue(final Object returnValue) {
        return MOCKERY.returnValue(returnValue);
    }

    public static void verify() {
        MOCKERY.verify();
    }
}
