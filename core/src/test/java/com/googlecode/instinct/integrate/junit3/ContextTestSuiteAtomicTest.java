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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public final class ContextTestSuiteAtomicTest extends InstinctTestCase {
    private static final Class<?> CONTEXT_CLASS = ASimpleContext.class;
    private TestSuite testSuite;

    @Override
    public void setUpSubject() {
        testSuite = new ContextTestSuite(CONTEXT_CLASS);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextTestSuite.class, TestSuite.class);
    }

    public void testConstructionWillCreateSpecifications() {
        expect.that(testSuite.countTestCases()).isEqualTo(1);
        final TestCase specificationTestCase = (TestCase) testSuite.testAt(0);
        expect.that(specificationTestCase).isNotNull();
        expect.that(specificationTestCase.getName()).isEqualTo("toCheckVerification");
    }
}
