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
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.runner.cli.OptionArgument.FORMATTERS;
import fj.data.List;
import static fj.data.List.list;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ASettingsFactoryImpl {
    @Subject private SettingsFactoryImpl factory;

    @Before
    public void setUp() {
        factory = new SettingsFactoryImpl();
    }

    @Specification
    public void willReturnNullIfArgumentsAreNull() {
        expect.that(factory.extractFrom(null)).isNull();
    }

    @Specification
    public void willExtractEmptySettingsIfNoArgumentsArePresent() {
        final Settings settings = factory.extractFrom(List.<String>nil());
        expect.that(settings).isNotNull();
        expect.that(settings.getOptions()).isNotNull();
        expect.that(settings.getOptions()).isEmpty();
        expect.that(settings.getArguments()).isNotNull();
        expect.that(settings.getArguments()).isEmpty();
    }

    @Specification
    public void willExtractEmptySettingsIfNoOptionsArePresent() {
        final Settings settings = factory.extractFrom(list("arg1", "arg2", "arg3"));
        expect.that(settings).isNotNull();
        expect.that(settings.getOptions()).isNotNull();
        expect.that(settings.getOptions()).isEmpty();
        expect.that(settings.getArguments()).isNotNull();
        expect.that(settings.getArguments()).isOfSize(3);
        expect.that(settings.getArguments()).isEqualTo(list("arg1", "arg2", "arg3"));
    }

    @Specification
    public void willTreatOptionLikeArgumentsWhichDoNotResolveAsArguments() {
        final Settings settings = factory.extractFrom(List.single("-x"));
        expect.that(settings).isNotNull();
        expect.that(settings.getOptions()).isNotNull();
        expect.that(settings.getOptions()).isOfSize(0);
        expect.that(settings.getArguments()).isNotNull();
        expect.that(settings.getArguments()).isOfSize(1);
        expect.that(settings.getArguments()).containsItem("-x");
    }

    @Specification
    public void willExtractSettingsWithOptionsAndArgumentsWhenOptionsArePresent() {
        final Settings settings = factory.extractFrom(list("-f", "brief,xml", "specifications"));
        expect.that(settings).isNotNull();
        expect.that(settings.getOptions()).isNotNull();
        expect.that(settings.getOptions()).isOfSize(1);
        expect.that(settings.getOptions()).containsItem(FORMATTERS);
        expect.that(settings.getOption(FORMATTERS)).isEqualTo("brief,xml");
        expect.that(settings.getArguments()).isNotNull();
        expect.that(settings.getArguments()).isOfSize(1);
        expect.that(settings.getArguments()).containsItem("specifications");
    }

    @Specification
    public void willExtractSettingsWithOptionsAndArgumentsWhenOptionsAreInLongForm() {
        final Settings settings = factory.extractFrom(list("--formatters", "xml,verbose", "specifications"));
        expect.that(settings).isNotNull();
        expect.that(settings.getOptions()).isNotNull();
        expect.that(settings.getOptions()).isOfSize(1);
        expect.that(settings.getOptions()).containsItem(FORMATTERS);
        expect.that(settings.getOption(FORMATTERS)).isEqualTo("xml,verbose");
        expect.that(settings.getArguments()).isNotNull();
        expect.that(settings.getArguments()).isOfSize(1);
        expect.that(settings.getArguments()).containsItem("specifications");
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Option FORMATTERS has already been set")
    public void willRejectTheSameShortFormatOptionPresentedMoreThanOnce() {
        factory.extractFrom(list("-f", "brief", "-f", "brief"));
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Option FORMATTERS has already been set")
    public void willRejectTheSameLongFormatOptionPresentedMoreThanOnce() {
        factory.extractFrom(list("--formatters", "brief,xml", "--formatters", "verbose"));
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Option FORMATTERS has already been set")
    public void willRejectTheSameOptionPresentedMoreThanOnceInDifferentFormats() {
        factory.extractFrom(list("--formatters", "brief,xml", "-f", "xml"));
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Option FORMATTERS requires a value, but none was given")
    public void willNotAcceptAnOptionThatRequiresAValueWithoutAValue() {
        factory.extractFrom(list("--formatters"));
    }
}
