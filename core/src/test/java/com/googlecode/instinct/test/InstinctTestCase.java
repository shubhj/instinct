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
import java.lang.reflect.Field;
import junit.framework.TestCase;

@SuppressWarnings({"NoopMethodInAbstractClass", "ProhibitedExceptionDeclared"})
public abstract class InstinctTestCase extends TestCase {
    private static final String NO_ERRORS = "";

    protected void setUpAutoMocks() {
        final Field[] fields = getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (field.getName().startsWith("mock")) {
                field.setAccessible(true);
                final Class<?> type = field.getType();
                try {
                    field.set(this, Mocker12.mock(type));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

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
            Mocker12.verify();
            Mocker.verify();
        } catch (AssertionError t) {
            return t.getMessage();
        } finally {
            Mocker12.reset();
            Mocker.reset();
            tearDown();
        }
        return NO_ERRORS;
    }
}
