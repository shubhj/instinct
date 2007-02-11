package com.googlecode.instinct.integrate.junit;

public interface BehaviourContextRunStrategy {
    void onBehaviourContext(BehaviourContextClass behaviourContext);

    void onSpecification(SpecificationMethod specification);
}
