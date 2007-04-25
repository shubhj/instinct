package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class SpecificationMethodImpl implements SpecificationMethod {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final SpecificationContext specificationContext;

    public SpecificationMethodImpl(final SpecificationContext specificationContext) {
        checkNotNull(specificationContext);
        this.specificationContext = specificationContext;
    }

    @Suggest({"Add overloaded method that doesn't take a speclistener.", "Add spec listener using addSpecListener()?"})
    public SpecificationResult run(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListener.onSpecification(this);
        return specificationRunner.run(specificationContext);
    }

    @Suggest("Determine this lazily, rather than passing into constructor.")
    public SpecificationContext getSpecificationContext() {
        return specificationContext;
    }

    public String getName() {
        return specificationContext.getSpecificationMethod().getName();
    }
}
