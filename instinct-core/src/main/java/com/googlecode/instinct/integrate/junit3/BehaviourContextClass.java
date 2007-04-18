package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("This belongs in the main core")
public interface BehaviourContextClass {
    ContextResult run(BehaviourContextRunStrategy behaviourContextRunStrategy, SpecificationRunStrategy specificationRunStrategy);

    <T> Class<T> getType();

    String getName();
}
