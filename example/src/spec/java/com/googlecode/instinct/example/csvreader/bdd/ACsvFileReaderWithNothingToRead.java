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
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.verify;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
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
public final class ACsvFileReaderWithNothingToRead {
    @Subject private CsvFileReader csvFileReader;
    @Mock private CsvFile csvFile;
    @Stub(auto = false) private CsvLine[] noLines;
    @Stub private RuntimeException exception;

    @BeforeSpecification
    public void before() {
        noLines = new CsvLine[]{};
        csvFileReader = new CsvFileReaderImpl(csvFile);
    }

    @AfterSpecification
    public void after() {
        verify();
    }

    @Specification(expectedException = RuntimeException.class)
    public void closesTheUnderlyingFileOnAllExceptions() {
        expect.that(new Expectations() {
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

    @Specification
    public void returnsNoLines() {
        expect.that(new Expectations() {
            {
                one(csvFile).hasMoreLines(); will(returnValue(false));
                ignoring(csvFile).close();
            }
        });
        expect.that(csvFileReader.readLines()).isEqualTo(noLines);
    }
}
