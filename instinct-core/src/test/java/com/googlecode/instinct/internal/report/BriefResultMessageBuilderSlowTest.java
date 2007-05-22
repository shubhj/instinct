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

import static java.lang.System.getProperty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilder;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"HardcodedLineSeparator"})
public final class BriefResultMessageBuilderSlowTest extends InstinctTestCase {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final String TAB = "\t";
    private ResultMessageBuilder briefResultMessageBuilder;
    private ContextResult contextResult;
    private SpecificationResult succeedingSpec;
    private SpecificationResult failingSpec;
    private SpecificationRunStatus failureStatus;
    private SpecificationFailureMessageBuilder failureMessageBuilder;

    @Override
    public void setUpTestDoubles() {
        contextResult = new ContextResultImpl("Context");
        failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();
        final RuntimeException failureCause = new RuntimeException("Failure cause");
        failureStatus = new SpecificationRunFailureStatus(failureCause);
        failingSpec = new SpecificationResultImpl("fails", failureStatus, 1L);
        final SpecificationRunStatus successStatus = new SpecificationRunSuccessStatus();
        succeedingSpec = new SpecificationResultImpl("runs", successStatus, 1L);
        contextResult.addSpecificationResult(failingSpec);
        contextResult.addSpecificationResult(succeedingSpec);
    }

    @Override
    public void setUpSubject() {
        briefResultMessageBuilder = new BriefResultMessageBuilder();
    }

    public void testCreatesBriefContextResultMessages() {
        final String expectedContextMessage = "Context" + NEW_LINE
                + "- fails (FAILED)" + NEW_LINE + NEW_LINE
                + formatFailureCause()
                + "- runs";
        expect.that(briefResultMessageBuilder.buildMessage(contextResult)).equalTo(expectedContextMessage);
    }

    public void testCreatesBriefSpecificationSuccessResultMessages() {
        expect.that(briefResultMessageBuilder.buildMessage(succeedingSpec)).equalTo("runs");
    }

    public void testCreatesBriefSpecificationFailuresResultMessages() {
        final String expected = "fails (FAILED)" + NEW_LINE + NEW_LINE
                + formatFailureCause();
        expect.that(briefResultMessageBuilder.buildMessage(failingSpec)).equalTo(expected);
    }

    private String formatFailureCause() {
        final String failureCause = failureMessageBuilder.buildMessage(failureStatus);
        final Pattern startOfLine = compile("^", MULTILINE);
        final Matcher matcher = startOfLine.matcher(failureCause);
        return matcher.replaceAll(TAB);
    }
}
