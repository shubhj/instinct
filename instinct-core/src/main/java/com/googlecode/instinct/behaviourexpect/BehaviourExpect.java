package com.googlecode.instinct.behaviourexpect;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Expectations;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;

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

    public static <T> MethodInvocationMatcher that(final T mockedObject) {
        checkNotNull(mockedObject);
        return expect.that(mockedObject);
    }

    @Suggest("numberOfTimes becomes Cardinality")
    public static <T> MethodInvocationMatcher that(final T mockedObject, final InvocationMatcher numberOfTimes) {
        checkNotNull(mockedObject, numberOfTimes);
        return expect.that(mockedObject, numberOfTimes);
    }

    @Suggest("Return type should be ReceiverClause")
    public static InvocationMatcher once() {
        return expect.once();
    }

    @Suggest("Return type: Matcher<T>")
    public static Constraint anything() {
        return expect.anything();
    }

    @Suggest("Return type: Matcher<T>")
    public static Constraint eq(final Object argument) {
        checkNotNull(argument);
        return expect.eq(argument);
    }

    @Suggest("Return type: Matcher<T>")
    public static Constraint same(final Object argument) {
        checkNotNull(argument);
        return expect.same(argument);
    }

    public static <T> T one(final T mockedObject) {
        checkNotNull(mockedObject);
        return expect.one(mockedObject);
    }

    @Suggest("May need to make up return type, jMock 2 doesn't have a corresponding type.")
    public static IdentityBuilder will(final Stub stubAction) {
        checkNotNull(stubAction);
        return expect.will(stubAction);
    }

    @Suggest("Return type: Action")
    public static Stub returnValue(final Object returnValue) {
        checkNotNull(returnValue);
        return expect.returnValue(returnValue);
    }
}
