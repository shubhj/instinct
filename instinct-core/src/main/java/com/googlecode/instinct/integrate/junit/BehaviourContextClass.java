package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;

public interface BehaviourContextClass {
    BehaviourContextResult run(BehaviourContextRunListener runListener);

    String getName();
}
