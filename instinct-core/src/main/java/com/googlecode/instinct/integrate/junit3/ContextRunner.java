package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;

interface ContextRunner {
    ContextResult run(ContextClass contextClass, ContextRunStrategy contextRunStrategy, SpecificationRunStrategy specificationRunStrategy);
}
