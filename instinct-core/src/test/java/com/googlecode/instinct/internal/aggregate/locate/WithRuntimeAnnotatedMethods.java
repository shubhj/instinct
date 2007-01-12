package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.core.annotate.Specification;

class WithRuntimeAnnotatedMethods {
    @Override
    @Specification
    public String toString() {
        return super.toString();
    }

    @Override
    @Specification
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
