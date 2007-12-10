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

import com.googlecode.instinct.example.csvreader.CsvFile;
import com.googlecode.instinct.example.csvreader.CsvFileReader;
import com.googlecode.instinct.example.csvreader.CsvFileReaderImpl;
import com.googlecode.instinct.example.csvreader.CsvLine;
import com.googlecode.instinct.example.csvreader.CsvLineSplitter;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.Reflector.insertFieldValueUsingInferredType;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context(groups = {"osdc"})
public final class ACsvFileReaderWithLinesToRead {
    @Subject private CsvFileReader csvFileReader;
    @Mock private CsvFile csvFile;
    @Mock private CsvLineSplitter csvLineSplitter;
    @Stub private CsvLine[] parsedLines;
    @Stub private String line1;
    @Stub(auto = false) private String[] splitColumns;

    @BeforeSpecification
    public void before() {
        splitColumns = new String[]{"A", "B"};
        parsedLines = new CsvLine[]{new CsvLine("A", "B")};
        csvFileReader = new CsvFileReaderImpl(csvFile);
        insertFieldValueUsingInferredType(csvFileReader, csvLineSplitter);
    }

    @Specification
    public void parsesBothLinesAndSplitsThem() {
        expect.that(new Expectations() {
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
        expect.that(csvFileReader.readLines()).isEqualTo(parsedLines);
    }
}