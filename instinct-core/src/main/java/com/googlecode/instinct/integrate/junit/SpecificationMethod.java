package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.SpecificationResult;

public interface SpecificationMethod {
    SpecificationResult run(SpecificationRunListener specificationRunListener);

    String getName();
}
