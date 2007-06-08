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

package com.googlecode.instinct.test;

import static com.googlecode.instinct.expect.Mocker12.reset;
import static com.googlecode.instinct.expect.Mocker12.verify;
import junit.framework.TestCase;

@SuppressWarnings({"NoopMethodInAbstractClass", "ProhibitedExceptionDeclared"})
public abstract class InstinctTestCase extends TestCase {
    private static final String NO_ERRORS = "";

    @Override
    public final void runBare() throws Throwable {
        final String assertionError = doRunBare();
        if (assertionError.trim().length() != 0) {
            // Is there another way to report errors without throwing an exception? See the ED code. Need to propagate stack
            // traces.
            throw new AssertionError(assertionError);
        }
    }

    @Override
    public final void setUp() {
        // Don't allow setup.
    }

    public void setUpTestDoubles() {
    }

    public void setUpSubject() {
    }

    @SuppressWarnings({"ErrorNotRethrown"})
    private String doRunBare() throws Throwable {
        setUpTestDoubles();
        setUpSubject();
        try {
            runTest();
            verify();
        } catch (AssertionError t) {
            return t.getMessage();
        } finally {
            reset();
            tearDown();
        }
        return NO_ERRORS;
    }
}