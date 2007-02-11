package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.SpecificationResult;

public interface SpecificationRunStrategy {
    SpecificationResult onSpecification(SpecificationMethod specificationMethod);
}
