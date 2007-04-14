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

package com.googlecode.instinct.internal.mock;

import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.jmock.core.Invocation;
import org.jmock.core.InvocationMatcher;

public final class InvokeAnyTimesMatcherAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(InvokeAnyTimesMatcher.class, InvocationMatcher.class);
    }

    public void testMatchesAlwaysReturnsTrue() {
        assertTrue(new InvokeAnyTimesMatcher().matches(mock(Invocation.class)));
        assertTrue(new InvokeAnyTimesMatcher().matches(mock(Invocation.class)));
    }

    public void testHasDescriptionReturnsFalse() {
        assertFalse(new InvokeAnyTimesMatcher().hasDescription());
    }

    public void testDescribeToPassesThroughBuffer() {
        checkDescribeToPassesThroughBuffer("SomeString");
        checkDescribeToPassesThroughBuffer("AnotherString");
    }

    private void checkDescribeToPassesThroughBuffer(final String content) {
        final StringBuffer buffer = new StringBuffer().append(content);
        final StringBuffer returnedBuffer = new InvokeAnyTimesMatcher().describeTo(buffer);
        assertSame(buffer, returnedBuffer);
        assertEquals(buffer.toString(), returnedBuffer.toString());
    }
}
