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

import com.googlecode.instinct.internal.mock.JMock12Mockery;
import com.googlecode.instinct.internal.mock.JMock12MockeryImpl;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsInstanceOf;

public final class Mocker12 {
    private static final JMock12Mockery JMOCK_12_MOCKERY = new JMock12MockeryImpl();

    private Mocker12() {
        throw new UnsupportedOperationException();
    }

    public static <T> T mock(final Class<T> toMock) {
        return JMOCK_12_MOCKERY.mock(toMock);
    }

    public static <T> T mock(final Class<T> toMock, final String roleName) {
        return JMOCK_12_MOCKERY.mock(toMock, roleName);
    }

    public static NameMatchBuilder expects(final Object mockedObject) {
        return JMOCK_12_MOCKERY.expects(mockedObject);
    }

    public static NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        return JMOCK_12_MOCKERY.expects(mockedObject, expectation);
    }

    public static InvocationMatcher once() {
        return JMOCK_12_MOCKERY.once();
    }

    public static InvocationMatcher times(final int expectedNumberOfCalls) {
        return JMOCK_12_MOCKERY.times(expectedNumberOfCalls);
    }

    public static InvocationMatcher atLeastOnce() {
        return JMOCK_12_MOCKERY.atLeastOnce();
    }

    public static InvocationMatcher anyTimes() {
        return JMOCK_12_MOCKERY.anyTimes();
    }

    public static Constraint anything() {
        return JMOCK_12_MOCKERY.anything();
    }

    public static <T> IsInstanceOf isA(final Class<T> operandClass) {
        return JMOCK_12_MOCKERY.isA(operandClass);
    }

    public static Constraint eq(final Object argument) {
        return JMOCK_12_MOCKERY.eq(argument);
    }

    public static Constraint same(final Object argument) {
        return JMOCK_12_MOCKERY.same(argument);
    }

    public static Constraint sameElements(final Object[] argument) {
        return JMOCK_12_MOCKERY.sameElements(argument);
    }

    public static Stub returnValue(final Object returnValue) {
        return JMOCK_12_MOCKERY.returnValue(returnValue);
    }

    public static void verify() {
        JMOCK_12_MOCKERY.verify();
    }

    public static void reset() {
        JMOCK_12_MOCKERY.reset();
    }
}
