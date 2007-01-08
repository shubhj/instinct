package au.id.adams.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregator;
import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import au.id.adams.instinct.internal.aggregate.locate.ClassLocatorImpl;
import au.id.adams.instinct.internal.runner.BehaviourContextRunnerImpl;
import au.id.adams.instinct.internal.runner.BehaviourContextRunner;
import au.id.adams.instinct.internal.util.ClassName;
import au.id.adams.instinct.core.BehaviourContextConfigurationException;
import au.id.adams.instinct.test.InstinctTestCase;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;

@SuppressWarnings({"ProhibitedExceptionThrown"})
public final class AllBehaviourContextsSlowTest extends InstinctTestCase {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunnerImpl();
    private final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(AllBehaviourContextsSlowTest.class,
        new ClassLocatorImpl());

    public void testRunAllContexts() {
        runAllContexts();
    }

    private void runAllContexts() {
        final ClassName[] contextClasses = aggregator.getContexts();
        for (final ClassName contextClassName : contextClasses) {
            final Class<?> cls = edgeClass.forName(contextClassName.getFullyQualifiedName());
            invokeContextIgnoringConfigurationExceptions(cls);
        }
    }

    private <T> void invokeContextIgnoringConfigurationExceptions(final Class<T> cls) {
        try {
            contextRunner.run(cls);
        } catch (BehaviourContextConfigurationException ignored) {
        } catch (EdgeException e) {
            if (e.getCause() instanceof InvocationTargetException) {
                throw (RuntimeException) e.getCause().getCause();
            } else {
                throw e;
            }
        }
    }
}
