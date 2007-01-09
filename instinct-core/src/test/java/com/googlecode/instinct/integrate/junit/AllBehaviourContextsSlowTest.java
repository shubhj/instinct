package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.util.ClassName;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.test.InstinctTestCase;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;

@SuppressWarnings({"ProhibitedExceptionThrown"})
public final class AllBehaviourContextsSlowTest extends InstinctTestCase {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(AllBehaviourContextsSlowTest.class,
            new ClassLocatorImpl());
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunnerImpl();

    public void testRunAllContexts() {
        runAllContexts();
    }

    private void runAllContexts() {
        final ClassName[] contextClasses = aggregator.getContexts();
        System.out.println("contextClasses = " + Arrays.asList(contextClasses));
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
