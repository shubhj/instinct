package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;

interface BehaviourContextRunner {
    ContextResult run(BehaviourContextClass behaviourContextClass,
            BehaviourContextRunStrategy behaviourContextRunStrategy, SpecificationRunStrategy specificationRunStrategy);
}
