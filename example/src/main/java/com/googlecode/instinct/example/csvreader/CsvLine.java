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

package com.googlecode.instinct.example.csvreader;

import java.util.Arrays;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class CsvLine {
    private final String[] columns;

    public CsvLine(final String... columns) {
        checkNotNull((Object[]) columns);
        this.columns = columns;
    }

    public String getColumn(final int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.length) {
            throw new IllegalArgumentException("Invalid colum index, 0 >= columnIndex < " + columns.length);
        }
        return columns[columnIndex];
    }

    @SuppressWarnings({"InstanceofInterfaces", "CastToConcreteClass", "AccessingNonPublicFieldOfAnotherObject"})
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof CsvLine && Arrays.equals(((CsvLine) obj).columns, columns);
    }

    @Override
    public int hashCode() {
        return columns.hashCode();
    }

    @Override
    public String toString() {
        return columns == null || columns.length == 0 ? "[]" : Arrays.asList(columns).toString();
    }
}
