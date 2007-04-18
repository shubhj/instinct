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

package com.googlecode.instinct.internal.runner;

import java.util.List;
import static com.googlecode.instinct.expect.Mocker.atLeastOnce;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

@Suggest("Test new getters")
public final class BehaviourContextResultImplAtomicTest extends InstinctTestCase {
    private ContextResult contextResult;
    private SpecificationResult specificationResult;

    public void testConformsToClassTraits() {
        checkClass(ContextResultImpl.class, ContextResult.class);
    }

    public void testGetBehaviourContextName() {
        checkGetBahaviourContextName("name1");
        checkGetBahaviourContextName("name2");
    }

    public void testResultAddedAppearsInListReturnedFromGetResults() {
        contextResult.addSpecificationResult(specificationResult);
        final List<SpecificationResult> results = contextResult.getSpecificationResults();
        assertTrue(results.contains(specificationResult));
    }

    public void testAllSpecificationsCompletedSuccessfullyMeansContextCompletedSuccessfully() {
        expects(specificationResult).method("completedSuccessfully").will(returnValue(true));
        contextResult.addSpecificationResult(specificationResult);
        assertTrue(contextResult.completedSuccessfully());
    }

    public void testIfOneSpecificationsFailsContextFails() {
        final SpecificationResult result1 = mock(SpecificationResult.class);
        final SpecificationResult result2 = mock(SpecificationResult.class);
        expects(result1, atLeastOnce()).method("completedSuccessfully").will(returnValue(true));
        expects(result2, atLeastOnce()).method("completedSuccessfully").will(returnValue(false));
        contextResult.addSpecificationResult(result1);
        contextResult.addSpecificationResult(result2);
        assertFalse(contextResult.completedSuccessfully());
        assertEquals(1, contextResult.getNumberOfFailures());
        assertEquals(1, contextResult.getNumberOfSuccesses());
    }

    private void checkGetBahaviourContextName(final String behaviourContextName) {
        final ContextResult result = new ContextResultImpl(behaviourContextName);
        assertEquals(behaviourContextName, result.getBehaviourContextName());
    }

    @Override
    public void setUpTestDoubles() {
        specificationResult = mock(SpecificationResult.class);
    }

    @Override
    public void setUpSubject() {
        contextResult = new ContextResultImpl("AnEmptyStack");
    }
}
