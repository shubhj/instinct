package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;

interface BehaviourContextRunner {

    BehaviourContextResult run(BehaviourContextClass behaviourContextClass,
            BehaviourContextRunStrategy behaviourContextRunStrategy, SpecificationRunStrategy specificationRunStrategy);
}
