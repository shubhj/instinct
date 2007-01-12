package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@BehaviourContext
public class ContextWithInvalidlyMarkedSpecification1 {
    @Specification
    public String specificationReturnsAValue() {
        return "";
    }
}
