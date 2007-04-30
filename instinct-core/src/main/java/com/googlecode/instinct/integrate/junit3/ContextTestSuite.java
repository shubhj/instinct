package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.SpikedContextClass;
import com.googlecode.instinct.internal.core.SpikedContextClassImpl;
import com.googlecode.instinct.internal.core.SpikedContextRunListener;
import com.googlecode.instinct.internal.core.SpikedSpecificationListener;
import com.googlecode.instinct.internal.core.SpikedSpecificationMethod;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestSuite;

@Suggest({"Try and just use the interface Test rather than concrete extension.", "Get working for embedded contexts",
        "Why do we need to do suite.testAt(0) on an instance of one of these to get a nice heirarchy?"})
public final class ContextTestSuite extends TestSuite implements SpikedContextRunListener, SpikedSpecificationListener {
    @Suggest("Do we need to make this a field? Does it need to be shared to make JUnit integration work?")
    private TestSuite currentContextSuite;

    @Suggest({"Do we need to do runContext() in the constructor?", "Do we need to set the name?"})
    public <T> ContextTestSuite(final Class<T> contextType) {
        checkNotNull(contextType);
        final SpikedContextClass contextClass = new SpikedContextClassImpl(contextType);
        runContext(contextClass);
    }

    public void onContext(final SpikedContextClass context) {
        checkNotNull(context);
        currentContextSuite = new TestSuite(context.getName());
        addTest(currentContextSuite);
    }

    public void onSpecification(final SpikedSpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        currentContextSuite.addTest(new SpecificationTestCase(specificationMethod));
    }

    private void runContext(final SpikedContextClass context) {
        setName(context.getName());
        context.run(this, this);
    }
}
