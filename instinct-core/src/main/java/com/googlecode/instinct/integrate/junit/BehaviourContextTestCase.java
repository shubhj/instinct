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
import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.core.naming.SpecificationNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public final class BehaviourContextTestCase implements Test {
    private final MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunnerImpl();
    private final Class<?> specificationClass;

    public <T> BehaviourContextTestCase(final Class<T> specificationClass) {
        checkNotNull(specificationClass);
        this.specificationClass = specificationClass;
    }

    public int countTestCases() {
        return getNumberOfSpecificationMethods();
    }

    public void run(final TestResult result) {
        checkNotNull(result);
        try {
            runTest(result);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    @Override
    public String toString() {
        return specificationClass.getName();
    }

    // Note. This is heavily influenced to the implementation of junit.framework.TestResult.run().
    private void runTest(final TestResult result) {
        result.startTest(this);
        result.runProtected(this, new ContextProtectable(contextRunner, specificationClass));
        result.endTest(this);
    }

    @SuppressWarnings({"ProhibitedExceptionThrown"})
    private void handleException(final EdgeException e) {
        // Note. Need to dig down as reflection is pushed behind an edge.
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }

    @Suggest("Should we cache this?")
    private int getNumberOfSpecificationMethods() {
        final Method[] methods = methodLocator.locateAll(specificationClass, Specification.class, new SpecificationNamingConvention());
        return methods.length;
    }

    private static final class ContextProtectable implements Protectable {
        private final BehaviourContextRunner contextRunner;
        private final Class<?> specificationClass;

        private <T> ContextProtectable(final BehaviourContextRunner contextRunner, final Class<T> specificationClass) {
            this.contextRunner = contextRunner;
            this.specificationClass = specificationClass;
        }

        public void protect() {
            contextRunner.run(specificationClass);
        }
    }
}
