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
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import org.jmock.Expectations;

public final class SpecificationFailureMessageBuilderImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private SpecificationFailureMessageBuilder failureMessageBuilder;
    @Stub private Throwable failureCause;
    @Stub private Throwable rootCause;
    @Mock private ExceptionFinder exceptionFinder;
    private SpecificationRunStatus failureStatus;
    private SpecificationRunStatus successStatus;

    @Override
    public void setUpTestDoubles() {
        successStatus = new SpecificationRunSuccessStatus();
        failureStatus = new SpecificationRunFailureStatus(failureCause);
    }

    @Override
    public void setUpSubject() {
        failureMessageBuilder = createSubject(SpecificationFailureMessageBuilderImpl.class, exceptionFinder);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationFailureMessageBuilderImpl.class, SpecificationFailureMessageBuilder.class);
    }

    public void testDoesNotBuildAMessageWhenGivenASuccessStatus() {
        final String failureMessage = failureMessageBuilder.buildMessage(successStatus);
        expect.that(failureMessage).isEmpty();
    }

    public void testBuildsMessagesFromFailedSpecResults() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(exceptionFinder).getRootCause(failureCause);
                will(returnValue(rootCause));
            }
        });
        final String failureMessage = failureMessageBuilder.buildMessage(failureStatus);
        final String rootCauseStackTrace = getRootCauseStackTrace();
        expect.that(failureMessage).isEqualTo(rootCauseStackTrace);
    }

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private String getRootCauseStackTrace() {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        rootCause.printStackTrace(new PrintWriter(bytes, true));
        return bytes.toString();
    }
}
