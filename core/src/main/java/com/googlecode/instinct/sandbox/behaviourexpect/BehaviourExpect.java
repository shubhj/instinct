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

package com.googlecode.instinct.sandbox.behaviourexpect;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Expectations;

@Suggest("Merge or combine with state-based Expect.")
public final class BehaviourExpect {
    // SUPPRESS ConstantName {
    public static final BehaviourExpectations expect = new BehaviourExpectationsImpl();
    // } SUPPRESS ConstantName

    private BehaviourExpect() {
        throw new UnsupportedOperationException();
    }

    public static MethodInvocationMatcher that() {
        return expect.that();
    }

    public static void that(final Expectations expectations) {
        checkNotNull(expectations);
        expect.that(expectations);
    }

    //
    //    public static <T> MethodInvocationMatcher that(final T mockedObject) {
    //        checkNotNull(mockedObject);
    //        return expect.that(mockedObject);
    //    }
    //
    //    @Suggest("numberOfTimes becomes Cardinality")
    //    public static <T> MethodInvocationMatcher that(final T mockedObject, final InvocationMatcher numberOfTimes) {
    //        checkNotNull(mockedObject, numberOfTimes);
    //        return expect.that(mockedObject, numberOfTimes);
    //    }
    //
    //    @Suggest("Return type should be ReceiverClause")
    //    public static InvocationMatcher once() {
    //        return expect.once();
    //    }
    //
    //    @Suggest("Return type: Matcher<T>")
    //    public static Constraint anything() {
    //        return expect.anything();
    //    }
    //
    //    @Suggest("Return type: Matcher<T>")
    //    public static Constraint eq(final Object argument) {
    //        checkNotNull(argument);
    //        return expect.eq(argument);
    //    }
    //
    //    @Suggest("Return type: Matcher<T>")
    //    public static Constraint same(final Object argument) {
    //        checkNotNull(argument);
    //        return expect.same(argument);
    //    }
    //
    //    public static <T> T one(final T mockedObject) {
    //        checkNotNull(mockedObject);
    //        return expect.one(mockedObject);
    //    }
    //
    //    @Suggest("May need to make up return type, jMock 2 doesn't have a corresponding type.")
    //    public static IdentityBuilder will(final Stub stubAction) {
    //        checkNotNull(stubAction);
    //        return expect.will(stubAction);
    //    }
    //
    //    @Suggest("Return type: Action")
    //    public static Stub returnValue(final Object returnValue) {
    //        checkNotNull(returnValue);
    //        return expect.returnValue(returnValue);
    //    }
}
