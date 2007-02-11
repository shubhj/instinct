package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;

public interface SpecificationMethod {
    SpecificationResult run(SpecificationRunStrategy specificationRunStrategy);

    SpecificationContext getSpecificationContext();

    String getName();
}
