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

import java.text.MessageFormat;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@SuppressWarnings({"StaticNonFinalField"})
public final class XpathEvaluatorImpl implements XpathEvaluator {
    private final Document document;
    private XPath xpath = XPathFactory.newInstance().newXPath();

    public XpathEvaluatorImpl(final Document document) {
        this.document = document;
    }

    public String evaluate(final String expression) {
        return evaluate(expression, String.class, XPathConstants.NODE);
    }

    public <T> T evaluate(final String expression, final Class<T> javaType) {
        return evaluate(expression, javaType, XPathConstants.NODE);
    }

    @SuppressWarnings({"OverlyBroadCatchBlock"})
    public <T> T evaluate(final String expression, final Class<T> javaType, final QName xmlType) {
        final Object value;
        try {
            value = xpath.evaluate(expression, document, xmlType);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(MessageFormat.format("Unable to resolve XPath \"{0}\"", expression), e);
        }
        if (value == null) {
            return null;
        }
        final String constructorArg = XPathConstants.NODE.equals(xmlType) ? ((Node) value).getTextContent() : String.valueOf(value);
        try {
            return javaType.getConstructor(String.class).newInstance(constructorArg);
        } catch (Exception e) {
            throw new RuntimeException("Unable to resolve XML value to desired type", e);
        }
    }

    void setXpath(final XPath xpath) {
        this.xpath = xpath;
    }

    Document getDocument() {
        return document;
    }
}
