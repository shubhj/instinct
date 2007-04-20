package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;

public final class SpecificationMethodImpl implements SpecificationMethod {
    private final SpecificationContext specificationContext;

    public SpecificationMethodImpl(final SpecificationContext specificationContext) {
        this.specificationContext = specificationContext;
    }

    public SpecificationResult run(final SpecificationRunStrategy specificationRunStrategy) {
        return specificationRunStrategy.onSpecification(this);
    }

    public SpecificationContext getSpecificationContext() {
        return specificationContext;
    }

    public String getName() {
        return specificationContext.getSpecificationMethod().getName();
    }
}
