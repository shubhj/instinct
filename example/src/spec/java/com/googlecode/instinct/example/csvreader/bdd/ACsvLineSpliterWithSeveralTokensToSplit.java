/*
 * Copyright 2006-2007 Tom Adams
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

package com.googlecode.instinct.example.csvreader.bdd;

import com.googlecode.instinct.example.csvreader.CsvLineSplitter;
import com.googlecode.instinct.example.csvreader.CsvLineSplitterImpl;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context(groups = {"osdc"})
public final class ACsvLineSpliterWithSeveralTokensToSplit {
    @Subject private CsvLineSplitter lineSplitter;

    @BeforeSpecification
    public void before() {
        lineSplitter = new CsvLineSplitterImpl(',');
    }

    @Specification
    public void correctlySplitsInputIntoTokens() {
        final String[] split = lineSplitter.split("foo,bar,baz");
        expect.that(split).isEqualTo(new String[]{"foo", "bar", "baz"});
    }

    @Specification
    public void correctlyHandlesLinesWithNoDelimiterPresent() {
        final String[] split = lineSplitter.split("foobarbaz");
        expect.that(split).isEqualTo(new String[]{"foobarbaz"});
    }

    @Specification
    public void correctlyHandlesTwoDelimitersInSequence() {
        final String[] split = lineSplitter.split("foo,bar,,baz");
        expect.that(split).isEqualTo(new String[]{"foo", "bar", "", "baz"});
    }
}
