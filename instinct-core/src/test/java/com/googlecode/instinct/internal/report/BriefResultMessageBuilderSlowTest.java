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

package com.googlecode.instinct.internal.report;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"HardcodedLineSeparator"})
public final class BriefResultMessageBuilderSlowTest extends InstinctTestCase {
    private ResultMessageBuilder briefResultMessageBuilder;
    private RuntimeException failureCause;
    private ContextResult contextResult;
    private SpecificationResult succeedingSpec;
    private SpecificationResult failingSpec;

    @Override
    public void setUpTestDoubles() {
        failureCause = new RuntimeException("Failure cause");
        contextResult = new ContextResultImpl("Context");
        succeedingSpec = new SpecificationResultImpl("runs", new SpecificationRunSuccessStatus(), 1L);
        failingSpec = new SpecificationResultImpl("fails", new SpecificationRunFailureStatus(failureCause), 1L);
        contextResult.addSpecificationResult(succeedingSpec);
        contextResult.addSpecificationResult(failingSpec);
    }

    @Override
    public void setUpSubject() {
        briefResultMessageBuilder = new BriefResultMessageBuilder();
    }

    @Suggest("Remove.")
    public void testFoo() {
    }

    public void nsotestCreatesBriefMessages() {
        final String contextMessage = briefResultMessageBuilder.buildMessage(contextResult);
        final String expectedContextMessage = "Context\n"
                + "- runs\n"
                + "- fails (FAILED)\n"
                + "";
        expect.that(contextMessage).equalTo(expectedContextMessage);
        System.out.println(contextMessage);
    }
}
