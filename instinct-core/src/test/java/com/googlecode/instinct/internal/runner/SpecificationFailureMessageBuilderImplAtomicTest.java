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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;

public final class SpecificationFailureMessageBuilderImplAtomicTest extends InstinctTestCase {
    private SpecificationFailureMessageBuilder messageBuilder;
    private SpecificationRunStatus successStatus;
    private SpecificationRunStatus failureStatus;
    private Throwable failureCause;
    private ExceptionFinder exceptionFinder;
    private ObjectFactory objectFactory;
    private PrintWriter printWriter;
    private ByteArrayOutputStream byteArrayOutputStream;
    private String stackTrace;

    @Override
    public void setUpTestDoubles() {
        exceptionFinder = mock(ExceptionFinder.class);
        objectFactory = mock(ObjectFactory.class);
        failureCause = mock(Throwable.class);
        successStatus = new SpecificationRunSuccessStatus();
        failureStatus = new SpecificationRunFailureStatus(failureCause);
        byteArrayOutputStream = mock(ByteArrayOutputStream.class);
        printWriter = mock(PrintWriter.class);
        stackTrace = getInstance(String.class);
    }

    @Override
    public void setUpSubject() {
        messageBuilder = createSubject(SpecificationFailureMessageBuilderImpl.class, exceptionFinder, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationFailureMessageBuilderImpl.class, SpecificationFailureMessageBuilder.class);
    }

    public void testDoesNotBuildAMessageWhenGivenASuccessStatus() {
        final String result = messageBuilder.buildMessage(successStatus);
        expect.that(result).isEmpty();
    }

    public void testBuildsMessagesFromFailedSpecResults() {
        expects(exceptionFinder).method("getRootCause").will(returnValue(failureCause));
        expectStackTraceWritten();
        final String result = messageBuilder.buildMessage(failureStatus);
        expect.that(result).equalTo(stackTrace);
    }

    private void expectStackTraceWritten() {
        expects(objectFactory).method("create").with(eq(ByteArrayOutputStream.class), eq(new Object[]{})).will(returnValue(byteArrayOutputStream));
        expects(objectFactory).method("create").with(eq(PrintWriter.class), eq(new Object[]{byteArrayOutputStream, true})).will(
                returnValue(printWriter));
        expects(failureCause).method("printStackTrace").with(same(printWriter));
        expects(byteArrayOutputStream).method("toString").will(returnValue(stackTrace));
    }
}
