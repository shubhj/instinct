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

import com.googlecode.instinct.example.csvreader.CsvFile;
import com.googlecode.instinct.example.csvreader.CsvFileImpl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class CsvFileTest {
    private CsvFile noLineFile;
    private CsvFile oneLineFile;
    private CsvFile twoLineFile;

    @Before
    public void setup() {
        noLineFile = new CsvFileImpl("example/src/spec/resources/no_lines.csv");
        oneLineFile = new CsvFileImpl("example/src/spec/resources/one_line.csv");
        twoLineFile = new CsvFileImpl("example/src/spec/resources/two_lines.csv");
    }

    @Test
    public void canBeClosed() {
        noLineFile.close();
    }

    @Test
    public void aNoLineFileHasNoLinesToRead() {
        assertThat(noLineFile.hasMoreLines(), equalTo(false));
    }

    @Test(expected = IllegalStateException.class)
    public void qNoLineFileThrowsExceptionWhenLinesAttemptToBeRead() {
        noLineFile.readLine();
    }

    @Test
    public void aOneLineFileAlwaysHasLinesToReadWhenNoneAreRead() {
        assertThat(oneLineFile.hasMoreLines(), equalTo(true));
        assertThat(oneLineFile.hasMoreLines(), equalTo(true));
        assertThat(oneLineFile.hasMoreLines(), equalTo(true));
        assertThat(oneLineFile.hasMoreLines(), equalTo(true));
    }

    @Test
    public void aOneLineFileReadsOneLineThenHasNoMoreToRead() {
        assertThat(oneLineFile.hasMoreLines(), equalTo(true));
        assertThat(oneLineFile.readLine(), equalTo("A,B,C,D,E,F"));
        assertThat(oneLineFile.hasMoreLines(), equalTo(false));
    }

    @Test
    public void aTwoLineFileHasLinesToRead() {
        assertThat(twoLineFile.hasMoreLines(), equalTo(true));
        assertThat(twoLineFile.hasMoreLines(), equalTo(true));
    }

    @Test
    public void aTwoLineFileReadsTwoLinesThenHasNoMoreToRead() {
        assertThat(twoLineFile.hasMoreLines(), equalTo(true));
        assertThat(twoLineFile.readLine(), equalTo("A,B,C,D,E,F"));
        assertThat(twoLineFile.hasMoreLines(), equalTo(true));
        assertThat(twoLineFile.readLine(), equalTo("G,H,I,J,K,L"));
        assertThat(twoLineFile.hasMoreLines(), equalTo(false));
    }
}