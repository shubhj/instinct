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
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.report.ResultFormat.QUIET;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import static com.googlecode.instinct.report.ResultFormat.XML;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AResultMessageBuilderFactoryImpl {

    @Subject private ResultMessageBuilderFactory factory;

    @BeforeSpecification
    public void setUp() {
        factory = new ResultMessageBuilderFactoryImpl();
    }

    @Specification
    public void willReturnABriefResultMessageBuilderForBriefResultFormats() {
        expect.that(factory.createFor(BRIEF)).isAnInstanceOf(BriefResultMessageBuilder.class);
    }

    @Specification
    public void willReturnAQuietResultMessageBuilderForQuietResultFormats() {
        expect.that(factory.createFor(QUIET)).isAnInstanceOf(QuietResultMessageBuilder.class);
    }

    @Specification
    public void willReturnAVerboseResultMessageBuilderForVerboseResultFormats() {
        expect.that(factory.createFor(VERBOSE)).isAnInstanceOf(VerboseResultMessageBuilder.class);
    }

    @Specification
    public void willReturnAnXmlResultMessageBuilderForXmlResultFormats() {
        expect.that(factory.createFor(XML)).isAnInstanceOf(XmlResultMessageBuilder.class);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptANullResultFormat() {
        factory.createFor(null);
    }
}
