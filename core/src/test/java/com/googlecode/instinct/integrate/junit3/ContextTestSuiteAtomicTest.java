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
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public final class ContextTestSuiteAtomicTest extends InstinctTestCase {

    public void testConformsToClassTraits() {
        checkClass(ContextTestSuite.class, TestSuite.class);
    }

    @Fix("For some crazy reason JMock is expecting ContextClassImpl.getName() to be called.  Using ASimpleContext instead.")
    @Suggest("Because mocking isn't working this is looking more like an integration test.")
    public void testConstructionWillCreateSpecifications() {
//        expects(contextClass).method("getName").will(returnValue("testConstruction"));
//        new ContextTestSuite(contextClass);
        final ContextClass context = new ContextClassImpl(ASimpleContext.class);
        final TestSuite testSuite = new ContextTestSuite(context);
        expect.that(testSuite.countTestCases()).equalTo(1);
        final TestCase specificationTestCase = (TestCase) testSuite.testAt(0);
        expect.that(specificationTestCase).notNull();
        expect.that(specificationTestCase.getName()).equalTo("toCheckVerification");
    }
}
