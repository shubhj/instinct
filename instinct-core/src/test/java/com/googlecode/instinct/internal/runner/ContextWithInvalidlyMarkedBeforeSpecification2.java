package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@BehaviourContext
public class ContextWithInvalidlyMarkedBeforeSpecification2 {
    @BeforeSpecification
    public void setUpReturnsAValue(final String param) {
    }

    @Specification
    public void specification() {
    }
}
