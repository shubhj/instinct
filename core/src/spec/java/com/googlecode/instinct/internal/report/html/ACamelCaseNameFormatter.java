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
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ACamelCaseNameFormatter {
    @Subject private CamelCaseNameFormatter formatter;

    @BeforeSpecification
    public void createSubject() {
        formatter = new CamelCaseNameFormatter();
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void mustRefuseToConvertNullPackageAndClassToSentence() {
        formatter.convertToSentence(null);
    }

    @Specification
    public void hasNoEffectOnAnEmptyString() {
        expect.that(formatter.convertToSentence("")).isEqualTo("");
    }

    @Specification
    public void hasNoEffectOnWhitespace() {
        expect.that(formatter.convertToSentence("\t ")).isEqualTo("\t ");
        expect.that(formatter.convertToSentence(" \n")).isEqualTo(" \n");
    }

    @Specification
    public void willConvertCamelCaseIntoSentenceCase() {
        expect.that(formatter.convertToSentence("camelCaseExample")).isEqualTo("camel case example");
    }

    @Specification
    public void willConvertTitleCaseIntoSentenceCase() {
        expect.that(formatter.convertToSentence("TitleCaseExample")).isEqualTo("Title case example");
    }

    @Specification
    public void willIgnoreAnyTextPriorToTheFinalFullStopIfAny() {
        expect.that(formatter.convertToSentence("this.is.not.important.OnlyThisIsImportant")).isEqualTo("Only this is important");
    }
}
