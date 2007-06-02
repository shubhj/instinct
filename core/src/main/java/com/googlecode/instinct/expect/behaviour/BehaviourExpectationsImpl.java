package com.googlecode.instinct.expect.behaviour;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.jmock.Expectations;

public final class BehaviourExpectationsImpl implements BehaviourExpectations {
    public void that(final Expectations expectations) {
        checkNotNull(expectations);
    }
}
