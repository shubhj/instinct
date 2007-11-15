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

import java.io.BufferedReader;
import com.googlecode.instinct.edge.BufferedReaderEdge;
import com.googlecode.instinct.edge.BufferedReaderEdgeImpl;
import com.googlecode.instinct.edge.FileReaderEdge;
import com.googlecode.instinct.edge.FileReaderEdgeImpl;

@SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
public final class CsvFileImpl implements CsvFile {
    private final FileReaderEdge fileReaderEdge = new FileReaderEdgeImpl();
    private String currentLine;
    private BufferedReaderEdge bufferedReaderEdge;

    public CsvFileImpl(final String filename) {
        bufferedReaderEdge = new BufferedReaderEdgeImpl(new BufferedReader(fileReaderEdge.newFileReader(filename)));
        currentLine = bufferedReaderEdge.readLine();
    }

    public boolean hasMoreLines() {
        return currentLine != null;
    }

    public String readLine() {
        if (!hasMoreLines()) {
            throw new IllegalStateException("No lines to read");
        }
        final String buffer = currentLine;
        currentLine = bufferedReaderEdge.readLine();
        return buffer;
    }

    public void close() {
        bufferedReaderEdge.close();
    }
}
