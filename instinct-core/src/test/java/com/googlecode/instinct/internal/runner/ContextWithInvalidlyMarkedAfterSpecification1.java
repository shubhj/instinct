package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.Specification;

@BehaviourContext
public class ContextWithInvalidlyMarkedAfterSpecification1 {
    @BeforeSpecification
    public String setUpReturnsAValue() {
        return "";
    }

    @Specification
    public void specification() {
    }
}
