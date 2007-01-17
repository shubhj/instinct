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

import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorImpl;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.mock.instance.ConcreteGeneratorImpl;
import com.googlecode.instinct.internal.mock.instance.ProxyGenerator;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
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

    @SuppressWarnings({"unchecked", "JUnitTestCaseInProductSource"})
    private <T extends TestCase> Class<T> getClass(final JavaClassName className) {
        final String qualified = className.getFullyQualifiedName();
        return edgeClass.forName(qualified);
    }
}