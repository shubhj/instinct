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

public final class CsvFileImpl implements CsvFile {
    private final String filename;

    public CsvFileImpl(final String filename) {
        this.filename = filename;
    }

    public boolean hasMoreLines() {
        return false;
    }

    public String readLine() {
        if (!hasMoreLines()) {
            throw new IllegalStateException("No lines to read");
        }
        return null;
    }
}
