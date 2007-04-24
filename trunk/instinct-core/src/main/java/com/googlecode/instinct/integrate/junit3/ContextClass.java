package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"This belongs in the main core",
        "This class should contain the getBeforeSpec(), getAfterSpec() and getSpec() methods.",
        "The context runner can then use these.",
        "Need some way to indicate whether we want to run a single spec or all of them",
        "Maybe pass a single spec method into the constructor?"})
public interface ContextClass {
    ContextResult run(ContextRunStrategy contextRunStrategy, SpecificationRunStrategy specificationRunStrategy);

    <T> Class<T> getType();

    String getName();
}
