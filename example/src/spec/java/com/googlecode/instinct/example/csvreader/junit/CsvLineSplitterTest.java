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

package com.googlecode.instinct.example.csvreader.junit;

import static org.hamcrest.collection.IsArray.array;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@SuppressWarnings({"unchecked"})
@RunWith(JUnit4ClassRunner.class)
public final class CsvLineSplitterTest {
    private static final String NOTHING_TO_SPLIT = "";
    private CsvLineSplitter lineSplitter;

    @Before
    public void setup() {
        lineSplitter = new CsvLineSplitterImpl(',');
    }

    @Test
    public void returnsTheContentPassedWhenThereIsNothingToSplit() {
        final String[] split = lineSplitter.split(NOTHING_TO_SPLIT);
        assertThat(split.length, equalTo(1));
        assertThat(split, hasItemInArray(NOTHING_TO_SPLIT));
    }

    @Test
    public void correctlySplitsInputIntoTokens() {
        final String[] split = lineSplitter.split("foo,bar,baz");
        assertThat(split, array(equalTo("foo"), equalTo("bar"), equalTo("baz")));
        assertThat(split, equalTo(new String[]{"foo", "bar", "baz"}));
    }

    @Test
    public void correctlyHandlesLinesWithNoDelimiterPresent() {
        final String[] split = lineSplitter.split("foobarbaz");
        assertThat(split, equalTo(new String[]{"foobarbaz"}));
    }

    @Test
    public void correctlyHandlesTwoDelimitersInSequence() {
        final String[] split = lineSplitter.split("foo,bar,,baz");
        assertThat(split, equalTo(new String[]{"foo", "bar", "", "baz"}));
    }
}
