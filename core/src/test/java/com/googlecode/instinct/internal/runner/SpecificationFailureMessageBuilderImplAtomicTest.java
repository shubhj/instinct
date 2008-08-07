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
import com.googlecode.instinct.internal.util.AggregatingException;
import static com.googlecode.instinct.internal.util.exception.ExceptionUtil.stackTrace;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import fj.data.Option;

@SuppressWarnings({"UnusedDeclaration"})
public final class SpecificationFailureMessageBuilderImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private SpecificationFailureMessageBuilder failureMessageBuilder;
    @Stub(auto = false) private SpecificationFailureException failureCause;
    @Stub(auto = false) private AggregatingException rootCause;
    @Stub(auto = false) private SpecificationRunStatus failureStatus;
    @Stub(auto = false) private SpecificationRunStatus successStatus;

    @Override
    public void setUpTestDoubles() {
        rootCause = new AggregatingException("A message", List.<Throwable>nil().cons(new RuntimeException()));
        failureCause = new SpecificationFailureException("", rootCause);
        successStatus = new SpecificationRunSuccessStatus();
        failureStatus = new SpecificationRunFailureStatus(failureCause, Option.<Throwable>none());
    }

    @Override
    public void setUpSubject() {
        failureMessageBuilder = createSubject(SpecificationFailureMessageBuilderImpl.class);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationFailureMessageBuilderImpl.class, SpecificationFailureMessageBuilder.class);
    }

    public void testDoesNotBuildAMessageWhenGivenASuccessStatus() {
        final String failureMessage = failureMessageBuilder.buildMessage(successStatus);
        expect.that(failureMessage).isEmpty();
    }

    public void testBuildsMessagesFromFailedSpecResults() {
        final String failureMessage = failureMessageBuilder.buildMessage(failureStatus);
        expect.that(failureMessage).isEqualTo(stackTrace(rootCause));
    }
}
