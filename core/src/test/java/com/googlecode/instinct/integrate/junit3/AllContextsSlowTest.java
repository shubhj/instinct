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

package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.locate.ContextFinder;
import com.googlecode.instinct.internal.locate.ContextFinderImpl;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.marker.ContextConfigurationException;
import com.googlecode.instinct.marker.LifeCycleMethodConfigurationException;
import static com.googlecode.instinct.marker.annotate.Specification.ALL_GROUPS;
import com.googlecode.instinct.test.InstinctTestCase;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings({"ProhibitedExceptionThrown"})
public final class AllContextsSlowTest extends InstinctTestCase {
    private final ClassEdge edgeClass = new ClassEdgeImpl();
    private final ContextRunner contextRunner = new StandardContextRunner();

    public void testRunAllContexts() {
        runAllContexts();
    }

    private void runAllContexts() {
        final ContextFinder contextFinder = new ContextFinderImpl(AllContextsSlowTest.class);
        final JavaClassName[] contextClasses = contextFinder.getContextNames(ALL_GROUPS);
        for (final JavaClassName contextClassName : contextClasses) {
            final Class<?> cls = edgeClass.forName(contextClassName.getFullyQualifiedName());
            invokeContextIgnoringConfigurationExceptions(cls);
        }
    }

    private <T> void invokeContextIgnoringConfigurationExceptions(final Class<T> cls) {
        try {
            contextRunner.run(new ContextClassImpl(cls));
        } catch (ContextConfigurationException ignored) {
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
