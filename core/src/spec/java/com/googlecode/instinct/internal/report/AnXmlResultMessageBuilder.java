/*
 * Copyright 2008 Jeremy Mawson
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
import com.googlecode.instinct.internal.core.PendingSpecificationMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunPendingStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunSuccessStatus;
import com.googlecode.instinct.internal.util.AggregatingException;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.report.ResultMessageBuilder;
import com.googlecode.instinct.report.ResultMessageBuilderException;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import fj.data.Option;
import java.io.StringReader;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@RunWith(InstinctRunner.class)
public final class AnXmlResultMessageBuilder {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    @Subject private XmlResultMessageBuilder builder;
    private ContextResult successContextResult;
    private ContextResult failureContextResult;
    private SpecificationResult successSpecificationResult;
    private SpecificationResult failureSpecificationResult;

    @BeforeSpecification
    public void setUp() throws ResultMessageBuilderException {
        builder = new XmlResultMessageBuilder();
        try {
            final ContextClass successContextClass = new ContextClassImpl(ASuccess.class);
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
        checkClass(builder.getClass(), ResultMessageBuilder.class);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullContextResults() throws ResultMessageBuilderException {
        builder.buildMessage((ContextResult) null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void doesNotPermitMessagesToBeBuiltAgainstNullSpecificationResults() {
        builder.buildMessage((SpecificationResult) null);
    }

    @Specification
    public void buildsAnEmptyMessageOnSpecificationResultSuccess() {
        expect.that(builder.buildMessage(successSpecificationResult)).isEmpty();
    }

    @Specification
    public void buildsAnEmptyMessageOnSpecificationResultFailure() {
        expect.that(builder.buildMessage(failureSpecificationResult)).isEmpty();
    }

    @Specification
    public void buildsAnXmlReportOnContextResultSuccess() throws Exception {
        final Date startTime = nowIgnoringMilliseconds();
        final Document document = createDocumentFrom(builder.buildMessage(successContextResult));
        final Date endTime = nowIgnoringMilliseconds();
        expect.that(evaluateXpath(document, "/testsuite/@errors")).isEqualTo("0");
        expect.that(evaluateXpath(document, "/testsuite/@failures")).isEqualTo("0");
        expect.that(evaluateXpath(document, "/testsuite/@hostname")).isEqualTo(InetAddress.getLocalHost().getHostName());
        expect.that(evaluateXpath(document, "/testsuite/@name")).isEqualTo(ASuccess.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/@tests")).isEqualTo("1");
        expect.that(evaluateXpath(document, "/testsuite/@time")).isEqualTo("2.500");
        final Date reportDate = DATE_FORMAT.parse(evaluateXpath(document, "/testsuite/@timestamp"));
        expect.that(reportDate).isGreaterThanOrEqualTo(startTime);
        expect.that(reportDate).isLessThanOrEqualTo(endTime);
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@classname")).isEqualTo(ASuccess.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@name")).isEqualTo("aPassingMethod");
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@time")).isEqualTo("2.500");
    }

    @Specification
    public void buildsAnXmlReportOnContextResultFailure() throws Exception {
        final Date startTime = nowIgnoringMilliseconds();
        final Document document = createDocumentFrom(builder.buildMessage(failureContextResult));
        final Date endTime = nowIgnoringMilliseconds();
        expect.that(evaluateXpath(document, "/testsuite/@errors")).isEqualTo("0");
        expect.that(evaluateXpath(document, "/testsuite/@failures")).isEqualTo("1");
        expect.that(evaluateXpath(document, "/testsuite/@hostname")).isEqualTo(InetAddress.getLocalHost().getHostName());
        expect.that(evaluateXpath(document, "/testsuite/@name")).isEqualTo(AFailure.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/@tests")).isEqualTo("2");
        expect.that(evaluateXpath(document, "/testsuite/@time")).isEqualTo("3.000");
        final Date reportDate = DATE_FORMAT.parse(evaluateXpath(document, "/testsuite/@timestamp"));
        expect.that(reportDate).isGreaterThanOrEqualTo(startTime);
        expect.that(reportDate).isLessThanOrEqualTo(endTime);
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@classname")).isEqualTo(AFailure.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@name")).isEqualTo("aPassingMethod");
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@time")).isEqualTo("2.500");
        expect.that(nodeExists(document, "/testsuite/testcase[1]/failure")).isFalse();
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/@classname")).isEqualTo(AFailure.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/@name")).isEqualTo("aFailingMethod");
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/@time")).isEqualTo("0.500");
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/failure/@message")).isEqualTo("It went wrong");
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/failure/@type"))
                .isEqualTo("com.googlecode.instinct.internal.util.AggregatingException");
        expect.that(evaluateXpath(document, "/testsuite/testcase[2]/failure")).startsWith("It just didn't work");
    }

    @Specification
    public void buildsAnXmlReportIncludingAPendingSpecification() throws Exception {
        final ContextClass successContextClass = new ContextClassImpl(ASuccess.class);
        final ContextResult successContextResultWithPending = new ContextResultImpl(successContextClass);
        final SpecificationMethod pendingSpecificationMethod = new PendingSpecificationMethod(ASuccess.class,
                ASuccess.class.getDeclaredMethod("aPendingMethod"), List.<LifecycleMethod>nil(), List.<LifecycleMethod>nil());
        final SpecificationResult pendingSpecificationResult =
                new SpecificationResultImpl(pendingSpecificationMethod, new SpecificationRunPendingStatus(), 700L);
        successContextResultWithPending.addSpecificationResult(pendingSpecificationResult);

        final Date startTime = nowIgnoringMilliseconds();
        final Document document = createDocumentFrom(builder.buildMessage(successContextResultWithPending));
        final Date endTime = nowIgnoringMilliseconds();
        expect.that(evaluateXpath(document, "/testsuite/@errors")).isEqualTo("0");
        expect.that(evaluateXpath(document, "/testsuite/@failures")).isEqualTo("0");
        expect.that(evaluateXpath(document, "/testsuite/@hostname")).isEqualTo(InetAddress.getLocalHost().getHostName());
        expect.that(evaluateXpath(document, "/testsuite/@name")).isEqualTo(ASuccess.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/@tests")).isEqualTo("1");
        expect.that(evaluateXpath(document, "/testsuite/@time")).isEqualTo("0.700");
        final Date reportDate = DATE_FORMAT.parse(evaluateXpath(document, "/testsuite/@timestamp"));
        expect.that(reportDate).isGreaterThanOrEqualTo(startTime);
        expect.that(reportDate).isLessThanOrEqualTo(endTime);
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@classname")).isEqualTo(ASuccess.class.getName());
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@name")).isEqualTo("aPendingMethod [PENDING - It's a whimsy]");
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/@time")).isEqualTo("0.700");
        expect.that(nodeExists(document, "/testsuite/testcase[1]/failure")).isFalse();
        expect.that(evaluateXpath(document, "/testsuite/testcase[1]/pending")).isEqualTo("It's a whimsy");
    }

    private Boolean nodeExists(final Object document, final String expression) throws Exception {
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        return node != null;
    }

    private Date nowIgnoringMilliseconds() {
        final Date date = new Date();
        date.setTime(date.getTime() - date.getTime() % 1000);
        return date;
    }

    private String evaluateXpath(final Object document, final String expression) throws Exception {
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        return node.getTextContent();
    }

    private Document createDocumentFrom(final String xmlString) throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        final DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder.parse(new InputSource(new StringReader(xmlString)));
    }

    private class AFailure {
        public final void aFailingMethod() {
        }
    }

    private class ASuccess {
        public final void aPassingMethod() {
        }
        @Specification(state = PENDING, reason = "It's a whimsy")
        public final void aPendingMethod() {
        }
    }
}