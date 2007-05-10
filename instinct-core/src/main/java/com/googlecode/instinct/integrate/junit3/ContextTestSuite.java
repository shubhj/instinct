package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import junit.framework.TestSuite;

@Suggest({"Try and just use the interface Test rather than concrete extension.",
        "Why do we need to do suite.testAt(0) on an instance of one of these to get a nice heirarchy?"})
public final class ContextTestSuite extends TestSuite implements ContextListener, SpecificationListener {
    private final ContextRunner contextRunner = new StandardContextRunner();
    private TestSuite currentContextSuite;

    public <T> ContextTestSuite(final Class<T> contextType) {
        checkNotNull(contextType);
        runContext(contextType);
    }

    public void preContextRun(final ContextClass contextClass) {
        checkNotNull(contextClass);
        currentContextSuite = new TestSuite(contextClass.getName());
        addTest(currentContextSuite);
    }

    public void postContextRun(final ContextClass contextClass, final ContextResult contextResult) {
        checkNotNull(contextClass, contextResult);
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        currentContextSuite.addTest(new SpecificationTestCase(specificationMethod));
    }

    public void postSpecificationMethod(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        checkNotNull(specificationMethod, specificationResult);
    }

    private <T> void runContext(final Class<T> contextType) {
        final ContextClass contextClass = new ContextClassImpl(contextType);
        setName(contextClass.getName());
        contextRunner.addContextListener(this);
        contextRunner.addSpecificationListener(this);
        contextRunner.run(contextClass);
    }
}
