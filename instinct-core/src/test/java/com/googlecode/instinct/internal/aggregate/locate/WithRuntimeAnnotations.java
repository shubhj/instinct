package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.annotate.BehaviourContext;

@BehaviourContext
public class WithRuntimeAnnotations {
    @Override
    @Specification
    public String toString() {
        System.out.println("true = " + true);
        return super.toString();
    }

    @Override
    @Specification
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
