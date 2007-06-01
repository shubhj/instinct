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

import com.googlecode.instinct.test.InstinctTestCase;
import junit.framework.AssertionFailedError;
import org.jmock.Mock;
import org.jmock.builder.InvocationMockerBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.matcher.AnyArgumentsMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;

@SuppressWarnings({"ErrorNotRethrown"})
public final class JMockMockControlAtomicTest extends InstinctTestCase {
    public void testGetMockedObject() {
        final MockControl control = new JMockMockControl(new Mock(Readable.class));
        final Object mock = control.createTestDouble();
        assertNotNull(mock);
        assertTrue(mock instanceof Readable);
    }

    public void testExpects() {
        final MockControl control = new JMockMockControl(new Mock(Readable.class));
        final NameMatchBuilder matchBuilder = control.expects(new AnyArgumentsMatcher());
        assertNotNull(matchBuilder);
        assertTrue(matchBuilder instanceof InvocationMockerBuilder);
    }

    public void testVerify() {
        try {
            final Mock mock = new Mock(Readable.class);
            mock.expects(new InvokeOnceMatcher()).method("read");
            new JMockMockControl(mock).verify();
            fail();
        } catch (AssertionFailedError expected) {
        }
    }

    public void testToString() {
        assertEquals("mockReadable", new JMockMockControl(new Mock(Readable.class)).toString());
    }
}
