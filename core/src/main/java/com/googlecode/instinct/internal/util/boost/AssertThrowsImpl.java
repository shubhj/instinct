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

package com.googlecode.instinct.internal.util.boost;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

public final class AssertThrowsImpl implements AssertThrows {
    private AssertException assertException = new AssertExceptionImpl();

    // SUGGEST AssertThrowsChecker.
    // SUGGEST Why is the caught exception returned. (TJA: For additional checking not supplied by this class. Smelly.)
    public Throwable assertThrows(final Class expectedException, final String message, final Runnable block) {
        final Throwable result = assertThrows(expectedException, block);
        assertException.checkExceptionMessage(message, result);
        return result;
    }

    // SUPPRESS IllegalCatch {
    public Throwable assertThrows(final Class expectedException, final Runnable block) {
        Throwable result = null;
        try {
            block.run();
            Assert.fail("Failed to throw exception: " + expectedException);
        } catch (AssertionFailedError e) {
            throw e;
        } catch (Throwable t) {
            assertException.checkExceptionClass(expectedException, t);
            result = t;
        }
        return result;
    }
    // } SUPPRESS IllegalCatch

    public void assertMessageContains(final Throwable t, final String fragment) {
        final String message = "Fragment '" + fragment + "' not found in message '" + t.getMessage() + "' ";
        Assert.assertTrue(message, t.getMessage().indexOf(fragment) > -1);
    }
}
