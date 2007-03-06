package com.googlecode.instinct.behaviourexpect;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.Stub;

// DEBT ConstantName {
public final class BehaviourExpect {
    @Suggest("Merge this field and the next one. Follow Ben's lead with the static expectations.")
    public static final BehaviourExpect expect = new BehaviourExpect();
    private static final BehaviourExpectations BEHAVIOUR_EXPECTATIONS = new BehaviourExpectationsImpl();

    private BehaviourExpect() {
        throw new UnsupportedOperationException();
    }

    public static <T> T one(final T mockedObject) {
        return BEHAVIOUR_EXPECTATIONS.one(mockedObject);
    }

    public static <T> NameMatchBuilder call(final T mockedObject) {
        return BEHAVIOUR_EXPECTATIONS.call(mockedObject);
    }

    public static MethodInvocations that() {
        return BEHAVIOUR_EXPECTATIONS.that();
    }

    public static void that(final Expectations expectations) {
        BEHAVIOUR_EXPECTATIONS.that(expectations);
    }

    public static <T> MethodInvocations that(final T mockedObject) {
        return BEHAVIOUR_EXPECTATIONS.that(mockedObject);
    }

    public static IdentityBuilder will(final Stub stubAction) {
        return BEHAVIOUR_EXPECTATIONS.will(stubAction);
    }

    public static Stub returnValue(final Object returnValue) {
        return BEHAVIOUR_EXPECTATIONS.returnValue(returnValue);
    }
}
// } DEBT ConstantName
