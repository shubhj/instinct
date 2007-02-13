package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.SpecificationResult;

public interface SpecificationRunStrategy {
    SpecificationResult onSpecification(SpecificationMethod specificationMethod);
}
