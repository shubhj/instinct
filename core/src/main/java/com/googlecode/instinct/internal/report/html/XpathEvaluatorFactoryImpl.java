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

import java.io.IOException;
import java.text.MessageFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.tools.ant.types.resources.FileResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class XpathEvaluatorFactoryImpl implements XpathEvaluatorFactory {
    private DocumentBuilderFactory documentBuilderFactory;

    public XpathEvaluatorFactoryImpl() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
    }

    public XpathEvaluator createFor(final FileResource resource) throws UnparseableContentException {
        final Document document;
        try {
            final DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            final InputSource inputSource = new InputSource(resource.getInputStream());
            document = docBuilder.parse(inputSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new UnparseableContentException(MessageFormat.format("File {0} could not be parsed", resource.getName()), e);
        } catch (IOException e) {
            throw new UnparseableContentException(MessageFormat.format("File {0} could not be parsed", resource.getName()), e);
        }
        return new XpathEvaluatorImpl(document);
    }
}
