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

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnXpathEvalutorFactoryImpl {
    @Specification
    public void dummy() {
    }

    /*
        @Subject private XpathEvaluatorFactoryImpl factory;
        @Mock
        private Document document;

        @Specification
        public void mustCreateAnXpathEvaluatorFactoryImpl() {
            factory = new XpathEvaluatorFactoryImpl();
            final XpathEvaluator xpathEvaluator = factory.createFor(document);
            assertNotNull(xpathEvaluator);
            assertTrue(xpathEvaluator instanceof XpathEvaluatorImpl);
            Assert.assertEquals(document, ((XpathEvaluatorImpl)xpathEvaluator).getDocument());
        }
    */
}
