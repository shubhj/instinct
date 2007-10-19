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

import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.aggregate.ContextClassAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.ContextAggregator;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.Test;
import junit.framework.TestSuite;

@Suggest("Move this (& all JUnit stuff) into a seperate distribution")
public final class JUnitTestSuiteBuilderImpl implements JUnitTestSuiteBuilder {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final ContextAggregator aggregator;

    public <T> JUnitTestSuiteBuilderImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        aggregator = new ContextClassAggregatorImpl(classInSpecTree);
    }

    public Test buildSuite(final String suiteName) {
        checkNotNull(suiteName);
        final JavaClassName[] contextClasses = aggregator.getContextNames();
        return buildSuite(suiteName, contextClasses);
    }

    private TestSuite buildSuite(final String suiteName, final JavaClassName[] contextClasses) {
        final TestSuite suite = new TestSuite(suiteName);
        for (final JavaClassName contextClass : contextClasses) {
            suite.addTest(new ContextTestSuite(getClass(contextClass)));
        }
        return suite;
    }

    @SuppressWarnings({"unchecked", "JUnitTestCaseInProductSource"})
    private <T> Class<T> getClass(final JavaClassName className) {
        final String qualified = className.getFullyQualifiedName();
        return edgeClass.forName(qualified);
    }
}
