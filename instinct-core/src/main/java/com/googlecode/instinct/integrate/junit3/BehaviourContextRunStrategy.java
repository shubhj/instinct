package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;

public interface BehaviourContextRunStrategy {
    ContextResult onBehaviourContext(BehaviourContextClass behaviourContext);
}
