/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.integrate.junit4;

import static com.googlecode.instinct.expect.Mocker12.eq;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ASuiteWithAContext;
import com.googlecode.instinct.internal.runner.JUnit4SuiteWithContextAnnotation;
import com.googlecode.instinct.test.InstinctTestCase;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public final class InstinctRunnerSlowTest extends InstinctTestCase {
    private RunNotifier notifier;
    private Runner instinctRunner;
    private Description description;

    @Override
    public void setUpTestDoubles() {
        notifier = mock(RunNotifier.class);
    }

    public void testRunsASuiteAWithContext() {
        final Class<ASuiteWithAContext> classToRun = ASuiteWithAContext.class;
        setupDescription(classToRun);
        runClass(classToRun);
    }

    public void testRunsJUnit4SuiteWithContextAnnotation() {
        setupDescription(ASimpleContext.class);
        runClass(JUnit4SuiteWithContextAnnotation.class);
    }

    private void setupDescription(final Class<?> classToRun) {
        description = Description.createTestDescription(classToRun, "toCheckVerification");
    }

    private void runClass(final Class<?> classToRun) {
        instinctRunner = new InstinctRunner(classToRun);
        runSuites(classToRun);
    }

    private void runSuites(final Class<?> cls) {
        expects(notifier).method("fireTestStarted").with(eq(description));
        expects(notifier).method("fireTestFinished").with(eq(description));
        instinctRunner.run(notifier);
    }
}
