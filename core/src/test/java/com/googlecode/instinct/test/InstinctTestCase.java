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

import com.googlecode.instinct.expect.Mocker12;
import com.googlecode.instinct.expect.behaviour.Mocker;
import static com.googlecode.instinct.test.mock.ActorAutoWirer.autoWireMockFields;
import static com.googlecode.instinct.test.mock.ActorAutoWirer.autoWireSubjectFields;
import junit.framework.TestCase;

@SuppressWarnings({"NoopMethodInAbstractClass", "ProhibitedExceptionDeclared", "ErrorNotRethrown"})
public abstract class InstinctTestCase extends TestCase {

    @Override
    public final void runBare() throws Throwable {
        autoWireMockFields(this);
        setUpTestDoubles();
        autoWireSubjectFields(this);
        setUpSubject();
        try {
            // FIX Wrap the runTest() in a try-catch so that we can still do verification afterwards. Don't lose either verification or test errors.
            // See ED codebase.
            runTest();
            Mocker12.verify();
            Mocker.verify();
        } finally {
            Mocker12.reset();
            Mocker.reset();
            tearDown();
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
        Throwable baseError = null;
        autoWireMockFields(this);
        setUpTestDoubles();
        setUpSubject();
        try {
            runTest();
            Mocker12.verify();
            Mocker.verify();
        } catch (AssertionError t) {
            baseError = t;
        } finally {
            Mocker12.reset();
            Mocker.reset();
            tearDown();
        }
        throw baseError;
    }
}
