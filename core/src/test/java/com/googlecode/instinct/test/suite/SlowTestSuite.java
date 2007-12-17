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

package com.googlecode.instinct.test.suite;

import com.googlecode.instinct.integrate.junit4.JUnit4InstinctRunnerSlowTest;
import static java.util.Arrays.asList;
import java.util.Enumeration;
import junit.framework.Test;
import junit.framework.TestSuite;

public final class SlowTestSuite {
    private static final TestAggregator AGGREGATOR = new TestAggregatorImpl(SlowTestSuite.class);

    private SlowTestSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
        final TestSuite testsFound = (TestSuite) AGGREGATOR.aggregate("Slow", ".*SlowTest");
        return filterOutExcludedTests(testsFound);
    }

    private static Test filterOutExcludedTests(final TestSuite testSuite) {
        final Enumeration<Test> enumeration = testSuite.tests();
        final TestSuite newTestSuite = new TestSuite("Slow");
        while (enumeration.hasMoreElements()) {
            final TestSuite suite = (TestSuite) enumeration.nextElement();
            if (!isExcluded(suite)) {
                newTestSuite.addTest(suite);
            }
        }
        return newTestSuite;
    }

    // Note. JUnit4InstinctRunnerSlowTest fails when run from IntelliJ, but runs in the Ant build fine.
    private static boolean isExcluded(final TestSuite testSuite) {
        final String[] excluded = {JUnit4InstinctRunnerSlowTest.class.getName()};
        return asList(excluded).contains(testSuite.getName());
    }
}
