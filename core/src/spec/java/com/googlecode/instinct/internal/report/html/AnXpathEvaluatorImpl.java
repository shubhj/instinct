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

package com.googlecode.instinct.internal.report.html;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import java.awt.Point;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.jmock.Expectations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@RunWith(InstinctRunner.class)
public final class AnXpathEvaluatorImpl {
    @Subject private XpathEvaluatorImpl evaluator;
    @Mock private Document document;
    @Mock private XPath xpath;
    @Mock private Node node;
    private final String expression = "//xpath";
    private final String textResult = "result";
    private final String numericResult = "128.5";

    @BeforeSpecification
    public void createSubject() {
        evaluator = new XpathEvaluatorImpl(document);
        evaluator.setXpath(xpath);
    }

    @Specification
    public void mustSuccessfullyEvaluateATextNodeToAString() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(returnValue(node));
                one(node).getTextContent();
                will(returnValue(textResult));
            }
        });
        assertEquals(textResult, evaluator.evaluate(expression));
    }

    @Specification(expectedException = RuntimeException.class)
    public void mustFailToEvaluateAnXpathThatCannotBeResolved() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(throwException(new XPathExpressionException("")));
            }
        });
        evaluator.evaluate(expression);
    }

    @Specification(expectedException = RuntimeException.class)
    public void mustFailToConstructAJavaTypeThatDoesNotHaveAStringOnlyConstructor() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(returnValue(node));
                one(node).getTextContent();
                will(returnValue(textResult));
            }
        });
        evaluator.evaluate(expression, Point.class);
    }

    @Specification(expectedException = RuntimeException.class)
    public void mustFailToConstructAJavaTypeWhenTheNodeTextDoesNotSuit() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(returnValue(node));
                one(node).getTextContent();
                will(returnValue(numericResult));
            }
        });
        evaluator.evaluate(expression, Integer.class);
    }

    @Specification
    public void mustSuccessfullyEvaluateATextNodeToSomethingOtherThanAString() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(returnValue(node));
                one(node).getTextContent();
                will(returnValue(numericResult));
            }
        });
        assertEquals(new Double(numericResult), evaluator.evaluate(expression, Double.class));
    }

    @Specification
    public void mustSuccessfullyEvaluateANonTextNodeToAString() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NUMBER);
                will(returnValue(textResult));
            }
        });
        assertEquals(textResult, evaluator.evaluate(expression, String.class, XPathConstants.NUMBER));
    }

    @Specification
    public void mustSuccessfullyEvaluateANonTextNodeToSomethingOtherThanAString() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NUMBER);
                will(returnValue(numericResult));
            }
        });
        assertEquals(new Double(numericResult), evaluator.evaluate(expression, Double.class, XPathConstants.NUMBER));
    }

    @Specification
    public void mustReturnNullIfTheXpathDoesNotEvaluate() throws XPathExpressionException {
        expect.that(new Expectations() {
            {
                one(xpath).evaluate(expression, document, XPathConstants.NODE);
                will(returnValue(null));
            }
        });
        assertNull(evaluator.evaluate(expression));
    }
}
