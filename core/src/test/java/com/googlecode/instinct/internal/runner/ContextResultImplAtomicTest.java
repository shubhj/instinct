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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.List;
import org.jmock.Expectations;

public final class ContextResultImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ContextResult contextResult;
    @Mock private SpecificationResult specificationResult1;
    @Mock private SpecificationResult specificationResult2;
    @Stub private String contextName;

    @Override
    public void setUpSubject() {
        contextResult = new ContextResultImpl(contextName);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextResultImpl.class, ContextResult.class);
    }

    public void testGetterReturnsContextNamePassedInConstructor() {
        final ContextResult result = new ContextResultImpl(contextName);
        expect.that(result.getContextName()).isEqualTo(contextName);
    }

    public void testResultAddedAppearsInListReturnedFromGetResults() {
        contextResult.addSpecificationResult(specificationResult1);
        final List<SpecificationResult> results = contextResult.getSpecificationResults();
        expect.that(results).containsItem(specificationResult1);
    }

    public void testAllSpecificationsCompletedSuccessfullyMeansContextCompletedSuccessfully() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specificationResult1).completedSuccessfully(); will(returnValue(true));
            }
        });
        contextResult.addSpecificationResult(specificationResult1);
        expect.that(contextResult.completedSuccessfully()).isTrue();
    }

    public void testContextFailsIfOneSpecificationsFails() {
        expect.that(new Expectations() {
            {
                allowing(specificationResult1).completedSuccessfully(); will(returnValue(false));
                allowing(specificationResult2).completedSuccessfully(); will(returnValue(true));
            }
        });
        contextResult.addSpecificationResult(specificationResult1);
        contextResult.addSpecificationResult(specificationResult2);
        expect.that(contextResult.completedSuccessfully()).isFalse();
        expect.that(contextResult.getNumberOfFailures()).isEqualTo(1);
        expect.that(contextResult.getNumberOfSuccesses()).isEqualTo(1);
    }
}
