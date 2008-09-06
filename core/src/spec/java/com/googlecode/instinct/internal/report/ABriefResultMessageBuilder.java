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
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.core.CompleteSpecificationMethod;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus;
import com.googlecode.instinct.internal.util.AggregatingException;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.report.ResultMessageBuilder;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import fj.data.Option;
import static java.lang.System.getProperty;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ABriefResultMessageBuilder {
    private static final String NL = getProperty("line.separator");
    private static final String TAB = "\t";

    @Subject private ResultMessageBuilder briefResultMessageBuilder = new BriefResultMessageBuilder();
    private ContextClass successContextClass;
    private ContextResult successContextResult;
    private SpecificationResult successSpecificationResult;
    private ContextResult failureContextResult;
    private SpecificationResult failureSpecificationResult;
    private static final String FAILURE_SPECIFICATION_MESSAGE_PREFIX = "aFailingMethod (FAILED)" + NL + NL + TAB + "It just didn't work" + NL + TAB +
            NL + TAB + "Summary: 1 error occurred" + NL + TAB + "1)  " + "Underlying cause" + NL + TAB + NL + TAB + "Full details follow:" + NL +
            TAB + NL + TAB + "1)  " + "java.lang.RuntimeException: Underlying cause";
    private static final String FAILURE_CONTEXT_MESSAGE_PREFIX =
            "AFailure" + NL + "- aPassingMethod" + NL + "- " + FAILURE_SPECIFICATION_MESSAGE_PREFIX;

    @BeforeSpecification
    public void setUp() {
        try {
            successContextClass = new ContextClassImpl(ASuccess.class);
            successContextResult = new ContextResultImpl(successContextClass);
            final SpecificationMethod successSpecificationMethod = new CompleteSpecificationMethod(ASuccess.class,
                    ASuccess.class.getDeclaredMethod("aPassingMethod"), List.<LifecycleMethod>nil(), List.<LifecycleMethod>nil());
            successSpecificationResult = new SpecificationResultImpl(successSpecificationMethod, new SpecificationRunSuccessStatus(), 2500L);
            successContextResult.addSpecificationResult(successSpecificationResult);
            failureContextResult = new ContextResultImpl(new ContextClassImpl(AFailure.class));
            final SpecificationMethod failureSpecificationMethod = new CompleteSpecificationMethod(AFailure.class,
                    AFailure.class.getDeclaredMethod("aFailingMethod"), List.<LifecycleMethod>nil(), List.<LifecycleMethod>nil());
            failureSpecificationResult = new SpecificationResultImpl(failureSpecificationMethod, new SpecificationRunFailureStatus(
                    new SpecificationFailureException("It went wrong",
                            new AggregatingException("It just didn't work", List.<Throwable>single(new RuntimeException("Underlying cause")))),
                    Option.<Throwable>none()), 500L);
            failureContextResult.addSpecificationResult(successSpecificationResult);
            failureContextResult.addSpecificationResult(failureSpecificationResult);
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(briefResultMessageBuilder.getClass(), ResultMessageBuilder.class);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullContextClasses() {
        briefResultMessageBuilder.buildMessage((ContextClass) null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullContextResults() {
        briefResultMessageBuilder.buildMessage((ContextResult) null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullSpecificationMethods() {
        briefResultMessageBuilder.buildMessage((SpecificationMethod) null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullSpecificationResults() {
        briefResultMessageBuilder.buildMessage((SpecificationResult) null);
    }

    @Specification
    public void createsBriefContextResultMessagesForContextsWithFails() {
        final String s = briefResultMessageBuilder.buildMessage(failureContextResult);
        expect.that(s).startsWith(FAILURE_CONTEXT_MESSAGE_PREFIX);
    }

    @Specification
    public void showsSuccessMessagesForContextResultWithNoFails() {
        expect.that(briefResultMessageBuilder.buildMessage(successContextResult)).isEqualTo("ASuccess" + NL + "- aPassingMethod");
    }

    @Specification
    public void createsBriefSpecificationSuccessResultMessages() {
        expect.that(briefResultMessageBuilder.buildMessage(successSpecificationResult)).isEqualTo("aPassingMethod");
    }

    @Specification
    public void createsBriefSpecificationFailureResultMessages() {
        expect.that(briefResultMessageBuilder.buildMessage(failureSpecificationResult)).startsWith(FAILURE_SPECIFICATION_MESSAGE_PREFIX);
    }

    @Specification
    public void createsBriefPreContextMessages() {
        expect.that(briefResultMessageBuilder.buildMessage(successContextClass)).isEqualTo("");
    }

    @Specification
    public void createsBriefPreSpecificationMessages() {
        expect.that(briefResultMessageBuilder.buildMessage(successContextClass)).isEqualTo("");
    }

    private class AFailure {
        public final void aFailingMethod() {
        }
    }

    private class ASuccess {
        public final void aPassingMethod() {
        }
    }
}
