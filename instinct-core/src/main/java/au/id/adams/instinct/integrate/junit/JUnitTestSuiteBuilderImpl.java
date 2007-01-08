package au.id.adams.instinct.integrate.junit;

import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregator;
import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import au.id.adams.instinct.internal.aggregate.locate.ClassLocatorImpl;
import au.id.adams.instinct.internal.util.ClassName;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public final class JUnitTestSuiteBuilderImpl implements JUnitTestSuiteBuilder {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final ProxyGenerator proxyGenerator = new ProxyGeneratorImpl();
    private final BehaviourContextAggregator aggregator;

    public <T> JUnitTestSuiteBuilderImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        aggregator = new BehaviourContextAggregatorImpl(classInSpecTree, new ClassLocatorImpl());
    }

    public Test buildSuite(final String suiteName) {
        checkNotNull(suiteName);
        final ClassName[] contextClasses = aggregator.getContexts();
        return buildSuite(suiteName, contextClasses);
    }

    private TestSuite buildSuite(final String suiteName, final ClassName[] contextClasses) {
        final TestSuite suite = new TestSuite(suiteName);
        for (final ClassName contextClass : contextClasses) {
            suite.addTest(createTestProxy(contextClass));
        }
        return suite;
    }

    private Test createTestProxy(final ClassName contextClass) {
        final Test test = new BehaviourContextTestCase(getClass(contextClass));
        return (Test) proxyGenerator.newProxy(Test.class, new BehaviourContextMethodInterceptorImpl(test));
    }

    @SuppressWarnings({"unchecked"})
    private <T extends TestCase> Class<T> getClass(final ClassName className) {
        final String qualified = className.getFullyQualifiedName();
        return edgeClass.forName(qualified);
    }
}
