package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.annotate.AfterSpecification;

@BehaviourContext
public class ContextWithInvalidlyMarkedAfterSpecification2 {
    @Specification
    public void specification() {
    }

    @AfterSpecification
    public void tearDownTakesAParam(final String param1) {
    }
}
