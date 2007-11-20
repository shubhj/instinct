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

import java.util.ArrayList;
import java.util.List;

public final class CsvFileReaderImpl implements CsvFileReader {
    private CsvLineSplitter lineSplitter = new CsvLineSplitterImpl(',');
    private final CsvFile csvFile;

    public CsvFileReaderImpl(final CsvFile csvFile) {
        this.csvFile = csvFile;
    }

    public CsvLine[] readLines() {
        try {
            return doReadLines();
        } finally {
            csvFile.close();
        }
    }

    private CsvLine[] doReadLines() {
        final List<CsvLine> lines = new ArrayList<CsvLine>();
        while (csvFile.hasMoreLines()) {
            lines.add(parseLine());
        }
        return lines.toArray(new CsvLine[lines.size()]);
    }

    private CsvLine parseLine() {
        final String line = csvFile.readLine();
        return new CsvLine(lineSplitter.split(line));
    }
}
