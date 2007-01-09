package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import com.googlecode.instinct.internal.util.ClassName;
import com.googlecode.instinct.test.InstinctTestCase;

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
        final List<ClassName> names = Arrays.asList(contextClasses);
        Collections.sort(names);
        System.out.println("contextClasses = " + names);
        System.out.println("contextClasses.length = " + contextClasses.length);
        for (final ClassName contextClassName : contextClasses) {
            final Class<?> cls = edgeClass.forName(contextClassName.getFullyQualifiedName());
            invokeContextIgnoringConfigurationExceptions(cls);
        }
    }

    /*
    com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$AConstructorWithParameters
    com.googlecode.instinct.internal.aggregate.ContextContainer1$AnEmbeddedPackageLocalContext
    com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$APrivateConstructor

    ANT: com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$AProtectedConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$APublicConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown, com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown$AnEmbeddedPublicContext

    IDEA: com.googlecode.instinct.internal.aggregate.ContextContainer1$AnEmbeddedPrivateContext, com.googlecode.instinct.internal.aggregate.ContextContainer1$AnEmbeddedPublicContext, com.googlecode.instinct.internal.aggregate.ContextContainer2$AnEmbeddedPackageLocalContext, com.googlecode.instinct.internal.aggregate.ContextContainer2$AnEmbeddedPrivateContext, com.googlecode.instinct.internal.aggregate.ContextContainer2$AnEmbeddedPublicContext, com.googlecode.instinct.internal.runner.ASimpleContext, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$AConstructorWithParameters, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$APackageLocalConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$APrivateConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$AProtectedConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithConstructors$APublicConstructor, com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown$AnEmbeddedPublicContext
    */

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
