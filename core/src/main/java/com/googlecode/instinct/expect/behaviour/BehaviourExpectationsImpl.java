package com.googlecode.instinct.expect.behaviour;

import static com.googlecode.instinct.expect.behaviour.Mocker.getMockery;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.jmock.internal.ExpectationBuilder;

public final class BehaviourExpectationsImpl implements BehaviourExpectations {
    public void that(final ExpectationBuilder expectations) {
        checkNotNull(expectations);
        getMockery().checking(expectations);
    }
}
