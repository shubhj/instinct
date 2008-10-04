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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNoWhitespace;
import com.googlecode.instinct.report.ResultFormat;
import java.util.EnumSet;

/**
 * Any argument to the command line interface which is to be interpreted as an option.
 */
public enum OptionArgument {
    FORMATTERS("f", "formatters", "LIST", "Overrides the default formatter with a comma-separated list of formatters. Choose from " +
            EnumSet.allOf(ResultFormat.class) + ". Each formatter can optionally be parameterised with {toDir=outputDirectory}");

    private static final String SHORT_FORM_PREFIX = "-";
    private static final String LONG_FORM_PREFIX = "--";
    private final String shortFormat;
    private final String longFormat;
    private final String valueKey;
    private final String description;

    OptionArgument(final String shortFormat, final String longFormat, final String valueKey, final String description) {
        this.shortFormat = shortFormat;
        this.longFormat = longFormat;
        this.valueKey = valueKey;
        this.description = description;
    }

    public String getShortFormat() {
        return shortFormat;
    }

    public String getLongFormat() {
        return longFormat;
    }

    public String getValueKey() {
        return valueKey;
    }

    public String getDescription() {
        return description;
    }

    public static OptionArgument indicatedBy(final String argument) {
        if (isArgumentSuitable(argument)) {
            for (final OptionArgument oa : EnumSet.allOf(OptionArgument.class)) {
                if (oa.isIndicatedBy(argument)) {
                    return oa;
                }
            }
        }
        return null;
    }

    public boolean isIndicatedBy(final Object argument) {
        return (LONG_FORM_PREFIX + longFormat).equals(argument) || (SHORT_FORM_PREFIX + shortFormat).equals(argument);
    }

    private static boolean isArgumentSuitable(final String argument) {
        if (argument == null || !argument.startsWith(SHORT_FORM_PREFIX)) {
            return false;
        }
        try {
            checkNoWhitespace(argument);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
