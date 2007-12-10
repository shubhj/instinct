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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import junit.framework.Assert;

public final class AssertExceptionImpl implements AssertException {

    public Throwable assertWraps(final Class expectedException, final Throwable wrapperException) {
        return assertWraps(expectedException, wrapperException, 1);
    }

    public Throwable assertWraps(final Class expectedException, final String expectedMessage, final Throwable wrapperException) {
        return assertWraps(expectedException, expectedMessage, wrapperException, 1);
    }

    public Throwable assertWraps(final Class expectedExceptionClass, final Throwable wrapperException, final int depthExceptionShouldAppearAt) {
        final int realDepth = depthExceptionShouldAppearAt <= 0 ? Integer.MAX_VALUE : depthExceptionShouldAppearAt;
        return checkWraps(expectedExceptionClass, wrapperException, realDepth);
    }

    public Throwable assertWraps(final Class expectedExceptionClass, 
            final String expectedMessage, final Throwable wrapperException, final int depthExceptionShouldAppearAt) {
        final Throwable cause = assertWraps(expectedExceptionClass, wrapperException, depthExceptionShouldAppearAt);
        checkExceptionMessage(expectedMessage, cause);
        return cause;
    }

    // SUPPRESS GenericIllegalRegex {
    public void checkExceptionClass(final Class expectedExceptionClass, final Throwable actual) {
        // SUGGEST Use something else.  Delegate.
        // SUGGEST How about a stacktrace object?
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        actual.printStackTrace(new PrintStream(out));
        final Class cls = actual.getClass();
        final String message = "Expected " + expectedExceptionClass + " but was " + cls + ", stack trace:\n";
        Assert.assertEquals(message + out, expectedExceptionClass, cls);
    }
    // } SUPPRESS GenericIllegalRegex

    public void checkExceptionMessage(final String expectedMessage, final Throwable actual) {
        Assert.assertEquals("Exception message doesn't match", expectedMessage, actual.getMessage());
    }

    private Throwable checkWraps(final Class expectedExceptionClass, final Throwable wrapperException, final int depth) {
        final Throwable cause = getCauseAtDepth(wrapperException, depth);
        checkExceptionClass(expectedExceptionClass, cause);
        return cause;
    }

    // SUPPRESS JavaNCSS {
    private Throwable getCauseAtDepth(final Throwable wrapperException, final int depth) {
        Throwable cause = wrapperException.getCause();
        boolean maxedOut = cause == wrapperException;
        int currentDepth = 1;
        while (!maxedOut && currentDepth < depth) {
            final Throwable currentWrapper = cause;
            cause = cause.getCause();
            maxedOut = cause == currentWrapper;
            currentDepth++;
        }
        if (depth != Integer.MAX_VALUE) {
            Assert.assertEquals("Wrapped exception not found at correct depth ", depth, currentDepth);
        }
        return cause;
    }
    // } SUPPRESS JavaNCSS
}
