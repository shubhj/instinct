/*
 * Copyright 2006-2007 Nicholas Partridge
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
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class QuietResultMessageBuilderAtomicTest extends InstinctTestCase {
    private static final String NEW_LINE = getProperty("line.separator");
    private static final String TAB = "\t";
    private ResultMessageBuilder quietResultMessageBuilder;
    private ContextResult failingContextResult;
    private ContextResult succeedingContextResult;
    private SpecificationResult succeedingSpec1;
    private SpecificationResult failingSpec;
    private SpecificationRunStatus failureStatus;
    private SpecificationFailureMessageBuilder failureMessageBuilder;

    @Override
    public void setUpTestDoubles() {
        succeedingContextResult = new ContextResultImpl("SucceedingContextName");
        failingContextResult = new ContextResultImpl("FailingContextName");
        failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();
        final RuntimeException failureCause = new RuntimeException("Failure cause");
        failureStatus = new SpecificationRunFailureStatus(failureCause);
        failingSpec = new SpecificationResultImpl("fails", failureStatus, 1L);
        final SpecificationRunStatus successStatus = new SpecificationRunSuccessStatus();
        succeedingSpec1 = new SpecificationResultImpl("runs", successStatus, 1L);
        succeedingContextResult.addSpecificationResult(succeedingSpec1);
        failingContextResult.addSpecificationResult(failingSpec);
        failingContextResult.addSpecificationResult(succeedingSpec1);
        failingContextResult.addSpecificationResult(new SpecificationResultImpl("soDoesThis", successStatus, 2L));
    }

    @Override
    public void setUpSubject() {
        quietResultMessageBuilder = new QuietResultMessageBuilder();
    }

    public void testConformsToClassTraits() {
        checkClass(BriefResultMessageBuilder.class, ResultMessageBuilder.class);
    }

    public void testCreatesQuietContextResultMessagesForContextsWithFails() {
        final String expectedContextMessage = "FailingContextName" + NEW_LINE
                + "- fails (FAILED)" + NEW_LINE + NEW_LINE
                + formatFailureCause();
        expect.that(quietResultMessageBuilder.buildMessage(failingContextResult)).isEqualTo(expectedContextMessage);
    }

    public void testShowsNoMessagesForContextResultWithNoFails() {
        expect.that(quietResultMessageBuilder.buildMessage(succeedingContextResult)).isEmpty();
    }

    public void testCreatesQuietSpecificationSuccessResultMessages() {
        expect.that(quietResultMessageBuilder.buildMessage(succeedingSpec1)).isEqualTo("");
    }

    public void testCreatesQuietSpecificationFailuresResultMessages() {
        final String expected = "fails (FAILED)" + NEW_LINE + NEW_LINE + formatFailureCause();
        expect.that(quietResultMessageBuilder.buildMessage(failingSpec)).isEqualTo(expected);
    }

    private String formatFailureCause() {
        final String failureCause = failureMessageBuilder.buildMessage(failureStatus);
        final Pattern startOfLine = compile("^", MULTILINE);
        final Matcher matcher = startOfLine.matcher(failureCause);
        return removeLastNewline(matcher.replaceAll(TAB));
    }

    private String removeLastNewline(final String failureCause) {
        return failureCause.endsWith(NEW_LINE) ? failureCause.substring(0, failureCause.length() - 1) : failureCause;
    }
}
