package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.runner.SpecificationListener;
import junit.framework.TestSuite;

@Fix("Make JUnit integration work again. Add to example ant build")
public final class ContextTestSuite extends TestSuite implements SpecificationListener {
    private ContextClass contextClass;

    public <T> ContextTestSuite(final Class<T> contextType) {
        checkNotNull(contextType);
        contextClass = new ContextClassImpl(contextType);
        contextClass.addSpecificationListener(this);
        contextClass.run();
    }

    @Override
    public String getName() {
        return contextClass.getName();
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        addTest(new SpecificationTestCase(specificationMethod));
    }

    public void postSpecificationMethod(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        checkNotNull(specificationMethod, specificationResult);
        // ignored
    }
}
