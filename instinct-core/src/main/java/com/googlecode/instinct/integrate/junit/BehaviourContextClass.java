package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("This belongs in the main core")
public interface BehaviourContextClass {
    BehaviourContextResult run(BehaviourContextRunStrategy behaviourContextRunStrategy, SpecificationRunStrategy specificationRunStrategy);

    <T> Class<T> getType();

    String getName();
}
