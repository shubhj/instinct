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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ACsvFileWithNoContent {
    @Subject private CsvFile csvFile;
    @Dummy private String filename;

    @BeforeSpecification
    public void before() {
        csvFile = new CsvFileImpl(filename);
    }

    @Specification
    public void hasNoLinesToRead() {
        expect.that(csvFile.hasMoreLines()).isFalse();
    }

    @Specification(expectedException = IllegalStateException.class, withMessage = "No lines to read")
    public void throwsExceptionWhenLinesAttemptToBeRead() {
        csvFile.readLine();
    }
}
