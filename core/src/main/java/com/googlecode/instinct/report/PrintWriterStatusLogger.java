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

package com.googlecode.instinct.report;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.io.PrintWriter;

public final class PrintWriterStatusLogger implements StatusLogger {
    private final PrintWriter writer;

    public PrintWriterStatusLogger(final PrintWriter writer) {
        checkNotNull(writer);
        this.writer = writer;
    }

    public void log(final String message) {
        checkNotNull(message);
        writer.println(message);
    }
}
