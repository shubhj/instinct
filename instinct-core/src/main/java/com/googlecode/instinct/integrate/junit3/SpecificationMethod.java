package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("This belongs in the main core")
public interface SpecificationMethod {
    SpecificationResult run(SpecificationRunStrategy specificationRunStrategy);

    SpecificationContext getSpecificationContext();

    String getName();
}
