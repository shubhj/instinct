package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@BehaviourContext
public class ContextWithInvalidlyMarkedSpecification2 {
    @Specification
    public void specificationTakesAParam(final String param1) {
    }
}
