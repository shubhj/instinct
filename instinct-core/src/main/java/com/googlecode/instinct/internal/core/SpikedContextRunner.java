package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.ContextResult;

interface SpikedContextRunner {
    ContextResult run(SpikedContextClass contextClass, SpikedSpecificationListener specificationListener);
}
