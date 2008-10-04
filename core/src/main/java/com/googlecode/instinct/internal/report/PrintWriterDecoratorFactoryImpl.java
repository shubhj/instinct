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

import com.googlecode.instinct.internal.runner.Formatter;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public class PrintWriterDecoratorFactoryImpl implements PrintWriterDecoratorFactory {
    public final PrintWriterDecorator createFor(final Formatter formatter) {
        checkNotNull(formatter);
        return formatter.getToDir() != null && formatter.getToDir().isDirectory() ? new FilePerContextPrintWriterDecorator(formatter.getToDir())
               : new SystemOutPrintWriterDecorator();
    }
}
