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
import com.googlecode.instinct.example.csvreader.CsvFileReader;
import com.googlecode.instinct.example.csvreader.CsvFileReaderImpl;
import com.googlecode.instinct.example.csvreader.CsvLine;
import com.googlecode.instinct.example.csvreader.CsvLineSplitter;
import com.googlecode.instinct.internal.util.Reflector;
import static org.hamcrest.core.IsEqual.equalTo;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class CsvFileReaderTest {
    private final Mockery mockery = new Mockery();
    private CsvFileReader csvFileReader;
    private CsvFile csvFile;
    private CsvLineSplitter csvLineSplitter;
    private CsvLine[] noLines;
    private RuntimeException exception;
    private CsvLine[] parsedLines;
    private String line1;
    private String[] splitColumns;

    @Before
    public void before() {
        csvFile = mockery.mock(CsvFile.class);
        csvLineSplitter = mockery.mock(CsvLineSplitter.class);
        noLines = new CsvLine[]{};
        exception = new RuntimeException();
        splitColumns = new String[]{"A", "B"};
        parsedLines = new CsvLine[]{new CsvLine("A", "B")};
        line1 = "A,B,C";
        csvFileReader = new CsvFileReaderImpl(csvFile);
        Reflector.insertFieldValueUsingInferredType(csvFileReader, csvLineSplitter);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }

    @Test(expected = RuntimeException.class)
    public void closesTheUnderlyingFileOnAllExceptions() {
        mockery.checking(new Expectations() {
            {
                one(csvFile).hasMoreLines();
                will(returnValue(true));
                one(csvFile).readLine();
                will(throwException(exception));
                atLeast(1).of(csvFile).close();
            }
        });
        csvFileReader.readLines();
    }

    @Test
    public void whenThereIsNothingToReadItReturnsNoLines() {
        mockery.checking(new Expectations() {
            {
                one(csvFile).hasMoreLines();
                will(returnValue(false));
                ignoring(csvFile).close();
            }
        });
        assertThat(csvFileReader.readLines(), equalTo(noLines));
    }

    @Test
    public void whenThereIsTwoLinesParsesBothLinesAndSplitsThem() {
        mockery.checking(new Expectations() {
            {
                one(csvFile).hasMoreLines();
                will(returnValue(true));
                one(csvFile).readLine();
                will(returnValue(line1));
                one(csvLineSplitter).split(line1);
                will(returnValue(splitColumns));
                one(csvFile).hasMoreLines();
                will(returnValue(false));
                ignoring(csvFile).close();
            }
        });
        assertThat(csvFileReader.readLines(), equalTo(parsedLines));
    }
}