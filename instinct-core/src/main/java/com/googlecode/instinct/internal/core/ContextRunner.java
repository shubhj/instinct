package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.ContextResult;

interface ContextRunner {
    ContextResult run(ContextClass contextClass, SpecificationListener specificationListener);
}
