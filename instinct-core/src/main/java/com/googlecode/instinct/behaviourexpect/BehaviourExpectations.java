package com.googlecode.instinct.behaviourexpect;

import com.googlecode.instinct.behaviourexpect.behaviour.jmock1.JMock1Expectations;
import com.googlecode.instinct.behaviourexpect.behaviour.jmock2.JMock2Expectations;
import com.googlecode.instinct.internal.util.Suggest;

public interface BehaviourExpectations extends DslExpectations, JMock1Expectations, JMock2Expectations {
    @Suggest("Find a home for this method.")
            <T> T one(final T mockedObject);
}
