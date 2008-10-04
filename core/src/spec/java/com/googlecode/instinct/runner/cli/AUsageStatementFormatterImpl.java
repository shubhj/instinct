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
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AUsageStatementFormatterImpl {
    private static final String NEW_LINE = System.getProperty("line.separator");

    @Subject UsageStatementFormatter formatter;

    @Before
    public void setUp() {
        formatter = new UsageStatementFormatterImpl();
    }

    @Specification
    public void returnsNullWhenFormattingANullOptionArgument() {
        expect.that(formatter.format(null)).isNull();
    }

    @Specification
    public void formatsOptionArgumentFormattersCorrectly() {
        expect.that(formatter.format(OptionArgument.FORMATTERS)).isEqualTo(getExpectedFormattersLayout());
    }

    private CharSequence getExpectedFormattersLayout() {
        final StringBuilder builder = new StringBuilder();
        builder.append("  -f, --formatters LIST   Overrides the default formatter with a comma-separated").append(NEW_LINE);
        builder.append("                          list of formatters. Choose from [QUIET, BRIEF,").append(NEW_LINE);
        builder.append("                          VERBOSE, XML]. Each formatter can optionally be").append(NEW_LINE);
        builder.append("                          parameterised with {toDir=outputDirectory}");
        return builder.toString();
    }
}
