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

package com.googlecode.instinct.internal.mock;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.matcher.InvokeCountMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.stub.ReturnStub;

public final class MockeryImpl implements Mockery {
    private final Verifier verifier = new VerifierImpl();
    private final TestDoubleHolder holder = new TestDoubleHolderImpl();
    private final MockCreator mockCreator = new JMockMockCreator();

    @SuppressWarnings({"unchecked"})
    public <T> T mock(final Class<T> toMock) {
        final TestDoubleControl control = mockCreator.createController(toMock);
        final Object mockedObject = createMockedObject(control);
        register(control, mockedObject);
        return (T) mockedObject;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T mock(final Class<T> toMock, final String roleName) {
        final TestDoubleControl control = mockCreator.createController(toMock, roleName);
        final Object mockedObject = createMockedObject(control);
        register(control, mockedObject);
        return (T) mockedObject;
    }

    public NameMatchBuilder expects(final Object mockedObject) {
        return expects(mockedObject, once());
    }

    @Suggest("Is there a better way to do the instance check? Maybe some interface like CanHaveExpectations set or such")
    public NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        final TestDoubleControl control = holder.getController(mockedObject);
        if (control instanceof MockControl) {
            return ((MockControl) control).expects(expectation);
        } else {
            throw new ExpectationNotAllowedException(mockedObject);
        }
    }

    public InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }

    public InvocationMatcher times(final int expectedNumberOfCalls) {
        return new InvokeCountMatcher(expectedNumberOfCalls);
    }

    public Constraint same(final Object argument) {
        return new IsSame(argument);
    }

    public Constraint anything() {
        return new IsAnything();
    }

    public Constraint eq(final Object argument) {
        return new IsEqual(argument);
    }

    public Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    public void verify() {
        verifier.verify();
    }

    @SuppressWarnings({"unchecked"})
    private <T> T createMockedObject(final TestDoubleControl mockControl) {
        return (T) mockControl.createTestDouble();
    }

    private void register(final TestDoubleControl mockControl, final Object mockedObject) {
        holder.addControl(mockControl, mockedObject);
        verifier.addVerifiable(mockControl);
    }
}
