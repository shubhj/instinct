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

package com.googlecode.instinct.verify;

import static java.text.MessageFormat.format;
import com.googlecode.instinct.internal.util.Suggest;

public final class Verify {
    private Verify() {
        throw new UnsupportedOperationException();
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustEqual(final Object expected, final Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(format("Must be: <{0}>, but was: <{1}>", expected, actual));
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustBeTrue(final boolean test) {
        if (!test) {
            fail("Must be true");
        }
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustBeFalse(final boolean test) {
        if (test) {
            fail("Must be false");
        }
    }

    private static void fail(final String message) {
        throw new VerificationException(message);
    }
}
