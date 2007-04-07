package com.googlecode.instinct.behaviourexpect;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Expectations;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.Stub;

@Suggest("Merge or combine with state-based Expect.")
public final class BehaviourExpect {
    // DEBT ConstantName {
    public static final BehaviourExpectations expect = new BehaviourExpectationsImpl();
    // } DEBT ConstantName

    private BehaviourExpect() {
        throw new UnsupportedOperationException();
    }

    public static MethodCardinalityInvocations that() {
        return expect.that();
    }

    public static void that(final Expectations expectations) {
        checkNotNull(expectations);
        expect.that(expectations);
    }

    public static <T> MethodCardinalityInvocations that(final T mockedObject) {
        checkNotNull(mockedObject);
        return expect.that(mockedObject);
    }

    public static Constraint same(final Object argument) {
        checkNotNull(argument);
        return expect.same(argument);
    }

    public static Constraint anything() {
        return expect.anything();
    }

    public static Constraint eq(final Object argument) {
        checkNotNull(argument);
        return expect.eq(argument);
    }

    public static <T> T one(final T mockedObject) {
        checkNotNull(mockedObject);
        return expect.one(mockedObject);
    }

    public static IdentityBuilder will(final Stub stubAction) {
        checkNotNull(stubAction);
        return expect.will(stubAction);
    }

    public static Stub returnValue(final Object returnValue) {
        checkNotNull(returnValue);
        return expect.returnValue(returnValue);
    }
}
