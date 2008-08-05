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

import com.googlecode.instinct.example.csvreader.CsvLine;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class CsvLineTest {
    private CsvLine csvLine;
    private String column1;
    private String column2;
    private String column3;

    @Before
    public void setup() {
        column1 = "A";
        column2 = "B";
        column3 = "C";
        csvLine = new CsvLine(column1, column2, column3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenTooLowAnIndexIsProvided() {
        csvLine.getColumn(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenTooHighAnIndexIsProvided() {
        csvLine.getColumn(3);
    }

    @Test
    public void providesAccessToColumnsByIndex() {
        assertThat(csvLine.getColumn(0), equalTo(column1));
        assertThat(csvLine.getColumn(1), equalTo(column2));
        assertThat(csvLine.getColumn(2), equalTo(column3));
    }

    @Test
    public void isEqualToObjectsWithTheSameColumns() {
        assertThat(new CsvLine(), equalTo(new CsvLine()));
        assertThat(new CsvLine("A"), equalTo(new CsvLine("A")));
        assertThat(new CsvLine("A", "B"), equalTo(new CsvLine("A", "B")));
    }

    @Test
    public void isNotEqualToObjectsWithTheDifferentColumns() {
        assertThat(new CsvLine(), not(equalTo(new CsvLine("A", "B"))));
        assertThat(new CsvLine("A", "A"), not(equalTo(new CsvLine("A", "B"))));
    }

    @Test
    public void isNotEqualToNull() {
        assertThat(new CsvLine(), not(equalTo(null)));
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void isNotEqualToDifferentClasses() {
        final String notACsvLine = "";
        assertThat(new CsvLine("").equals(notACsvLine), equalTo(false));
    }

    @Test
    public void hasAHasCodeThatIsThatOfTheColumns() {
        final String[] columns = {""};
        assertThat(new CsvLine(columns).hashCode(), equalTo(columns.hashCode()));
    }

    @Test
    public void providesColumnsAsAString() {
        assertThat(new CsvLine("A", "B"), hasToString(equalTo("[A, B]")));
    }

    @Test
    public void returnsAnEmptyStringWhenNoColumns() {
        assertThat(new CsvLine(), hasToString(equalTo("[]")));
    }
}