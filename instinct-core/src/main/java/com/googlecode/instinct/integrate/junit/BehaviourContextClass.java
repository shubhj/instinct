package com.googlecode.instinct.integrate.junit;

public interface BehaviourContextClass {
    String getName();

    void run(BehaviourContextRunStrategy runStrategy);
}
