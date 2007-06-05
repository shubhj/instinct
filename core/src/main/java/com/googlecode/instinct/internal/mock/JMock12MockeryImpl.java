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

import com.googlecode.instinct.internal.mock.constraint.ArrayElementsSame;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.matcher.InvokeAtLeastOnceMatcher;
import org.jmock.core.matcher.InvokeCountMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.stub.ReturnStub;

@Suggest("Do we want to reject nulls in these public methods?")
public final class JMock12MockeryImpl implements JMock12Mockery {
    private final Verifier verifier = new VerifierImpl();
    private Resetter resetter = new ResetterImpl();
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

//    public InvocationMatcher times(final int minNumberOfCalls, final int maxNumberOfCalls) {
//        return new InvokeCountMatcher();
//    }

    public InvocationMatcher atLeastOnce() {
        return new InvokeAtLeastOnceMatcher();
    }

    public InvocationMatcher anyTimes() {
        return new InvokeAnyTimesMatcher();
    }

    public Constraint same(final Object argument) {
        if (!canConstrainWithSame(argument)) {
            throw new IllegalArgumentException("Cannot constrain argument of type " + argument.getClass().getSimpleName()
                    + " with same(), use eq() instead");
        }
        return new IsSame(argument);
    }

    public Constraint anything() {
        return new IsAnything();
    }

    public <T> IsInstanceOf isA(final Class<T> operandClass) {
        return new IsInstanceOf(operandClass);
    }

    public Constraint eq(final Object argument) {
        return new IsEqual(argument);
    }

    public Constraint sameElements(final Object[] argument) {
        return new ArrayElementsSame(argument);
    }

    public Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    public void verify() {
        verifier.verify();
    }

    public void reset() {
        resetter.reset();
    }

    private boolean canConstrainWithSame(final Object argument) {
        return argument != null && nullSafeCanConstrainWithSame(argument);
    }

    private boolean nullSafeCanConstrainWithSame(final Object argument) {
        return !argument.getClass().equals(String.class) && !argument.getClass().isPrimitive() && !argument.getClass().isArray();
    }

    @SuppressWarnings({"unchecked"})
    private <T> T createMockedObject(final TestDoubleControl mockControl) {
        return (T) mockControl.createTestDouble();
    }

    private void register(final TestDoubleControl mockControl, final Object mockedObject) {
        holder.addControl(mockControl, mockedObject);
        verifier.addVerifiable(mockControl);
        resetter.addResetable(mockControl);
    }
}
