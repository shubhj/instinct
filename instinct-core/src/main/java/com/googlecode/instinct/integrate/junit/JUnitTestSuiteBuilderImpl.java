package com.googlecode.instinct.integrate.junit;

import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.mock.instance.ProxyGenerator;
import com.googlecode.instinct.internal.mock.instance.ConcreteGeneratorImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@Suggest("Move this into a seperate distribution")
public final class JUnitTestSuiteBuilderImpl implements JUnitTestSuiteBuilder {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final ProxyGenerator proxyGenerator = new ConcreteGeneratorImpl();
    private final BehaviourContextAggregator aggregator;

    public <T> JUnitTestSuiteBuilderImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        aggregator = new BehaviourContextAggregatorImpl(classInSpecTree, new ClassLocatorImpl());
    }

    public Test buildSuite(final String suiteName) {
        checkNotNull(suiteName);
        final JavaClassName[] contextClasses = aggregator.getContextNames();
        return buildSuite(suiteName, contextClasses);
    }

    private TestSuite buildSuite(final String suiteName, final JavaClassName[] contextClasses) {
        final TestSuite suite = new TestSuite(suiteName);
        for (final JavaClassName contextClass : contextClasses) {
            suite.addTest(createTestProxy(contextClass));
        }
        return suite;
    }

    private Test createTestProxy(final JavaClassName contextClass) {
        final Test test = new BehaviourContextTestCase(getClass(contextClass));
        return (Test) proxyGenerator.newProxy(Test.class, new BehaviourContextMethodInterceptorImpl(test));
    }

    @SuppressWarnings({"unchecked"})
    private <T extends TestCase> Class<T> getClass(final JavaClassName className) {
        final String qualified = className.getFullyQualifiedName();
        return edgeClass.forName(qualified);
    }
}
