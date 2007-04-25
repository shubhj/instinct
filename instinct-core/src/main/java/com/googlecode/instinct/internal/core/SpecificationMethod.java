package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("This belongs in the main core")
public interface SpecificationMethod {
    SpecificationResult run(SpecificationListener specificationListener);

    SpecificationContext getSpecificationContext();

    String getName();
}
