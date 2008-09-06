/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.internal.report;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import java.io.PrintWriter;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class SystemOutPrintWriterDecorator implements PrintWriterDecorator {
    private static final boolean AUTO_FLUSH = true;
    private static final PrintWriter SYS_OUT_WRITER = new PrintWriter(System.out, AUTO_FLUSH);

    public void printBefore(final ContextClass contextClass, final String message) {
        SYS_OUT_WRITER.println(message);
    }

    public void printBefore(final SpecificationMethod specificationMethod, final String message) {
        SYS_OUT_WRITER.println(message);
    }

    public void printAfter(final ContextResult contextResult, final String message) {
        SYS_OUT_WRITER.println(message);
    }

    public void printAfter(final SpecificationResult specificationResult, final String message) {
        SYS_OUT_WRITER.println(message);
    }

    public void printAfterAll(final String message) {
        SYS_OUT_WRITER.println(message);
    }
}
