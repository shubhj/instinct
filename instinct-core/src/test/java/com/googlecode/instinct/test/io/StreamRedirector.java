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

package com.googlecode.instinct.test.io;

import java.io.OutputStream;
import java.io.PrintStream;
import static java.lang.System.setOut;

public final class StreamRedirector {
    private StreamRedirector() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "IOResourceOpenedButNotSafelyClosed"})
    public static void doWithRedirectedStandardOut(final OutputStream redirectTargetStream, final Runnable block) {
        final PrintStream defaultStdOut = System.out;
        try {
            setOut(new PrintStream(redirectTargetStream));
            block.run();
        } finally {
            setOut(defaultStdOut);
        }
    }
}
