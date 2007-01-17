/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.InvocationTargetException;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.core.LifeCycleMethodConfigurationException;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"ProhibitedExceptionThrown"})
public final class AllBehaviourContextsSlowTest extends InstinctTestCase {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final BehaviourContextAggregator behaviourContextAggregator = new BehaviourContextAggregatorImpl(AllBehaviourContextsSlowTest.class,
            new ClassLocatorImpl());
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunnerImpl();

    public void testRunAllContexts() {
        runAllContexts();
    }

    private void runAllContexts() {
        final JavaClassName[] contextClasses = behaviourContextAggregator.getContextNames();
        for (final JavaClassName contextClassName : contextClasses) {
            final Class<?> cls = edgeClass.forName(contextClassName.getFullyQualifiedName());
            invokeContextIgnoringConfigurationExceptions(cls);
        }
    }

    private <T> void invokeContextIgnoringConfigurationExceptions(final Class<T> cls) {
        try {
            contextRunner.run(cls);
        } catch (BehaviourContextConfigurationException ignored) {
        } catch (LifeCycleMethodConfigurationException ignored) {
        } catch (EdgeException e) {
            if (e.getCause() instanceof InvocationTargetException) {
                throw (RuntimeException) e.getCause().getCause();
            } else {
                throw e;
            }
        }
    }
}