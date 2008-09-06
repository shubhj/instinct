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

package com.googlecode.instinct.runner.cli;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class TheOptionArgumentEnumeration {

    @Specification
    public void willReturnEnumElementsByTheirShortId() {
        expect.that(OptionArgument.indicatedBy("-f")).isEqualTo(OptionArgument.FORMATTERS);
    }

    @Specification
    public void willReturnEnumElementsByTheirLongId() {
        expect.that(OptionArgument.indicatedBy("--formatters")).isEqualTo(OptionArgument.FORMATTERS);
    }

    @Specification
    public void willReturnNullIfIdIsNotRecognised() {
        expect.that(OptionArgument.indicatedBy("x")).isNull();
        expect.that(OptionArgument.indicatedBy("-F")).isNull();
        expect.that(OptionArgument.indicatedBy("--formatters=")).isNull();
        expect.that(OptionArgument.indicatedBy("--formatters=xml")).isNull();
    }

    @Specification
    public void willReturnNullIfIdIsNull() {
        expect.that(OptionArgument.indicatedBy(null)).isNull();
    }

    @Specification
    public void willReturnNullIfIdContainsWhitespace() {
        expect.that(OptionArgument.indicatedBy("-f\t--formatters")).isNull();
        expect.that(OptionArgument.indicatedBy("--formatters\t-f")).isNull();
    }

    @Specification
    public void willIndicateIfAnElementIsIndicatedByItsShortFormArgument() {
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("-f")).isTrue();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("f")).isFalse();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("-F")).isFalse();
    }

    @Specification
    public void willIndicateIfAnElementIsIndicatedByItsLongFormArgument() {
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("--formatters")).isTrue();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("--formatters=")).isFalse();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("--formatters=brief,verbose,xml")).isFalse();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("-formatters")).isFalse();
        expect.that(OptionArgument.FORMATTERS.isIndicatedBy("--Formatters")).isFalse();
    }
}
