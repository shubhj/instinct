package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.XxxContextClass;
import com.googlecode.instinct.internal.core.XxxContextClassImpl;
import com.googlecode.instinct.internal.core.XxxContextRunListener;
import com.googlecode.instinct.internal.core.SpecificationListener;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestSuite;

@Suggest({"Try and just use the interface Test rather than concrete extension.", "Get working for embedded contexts",
        "Why do we need to do suite.testAt(0) on an instance of one of these to get a nice heirarchy?"})
public final class ContextTestSuite extends TestSuite implements XxxContextRunListener, SpecificationListener {
    @Suggest("Do we need to make this a field? Does it need to be shared to make JUnit integration work?")
    private TestSuite currentContextSuite;

    @Suggest({"Do we need to do runContext() in the constructor?", "Do we need to set the name?"})
    public <T> ContextTestSuite(final Class<T> contextType) {
        checkNotNull(contextType);
        final XxxContextClass contextClass = new XxxContextClassImpl(contextType);
        runContext(contextClass);
    }

    public void onContext(final XxxContextClass context) {
        checkNotNull(context);
        currentContextSuite = new TestSuite(context.getName());
        addTest(currentContextSuite);
    }

    public void onSpecification(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        currentContextSuite.addTest(new SpecificationTestCase(specificationMethod));
    }

    private void runContext(final XxxContextClass context) {
        setName(context.getName());
        context.run(this, this);
    }
}
