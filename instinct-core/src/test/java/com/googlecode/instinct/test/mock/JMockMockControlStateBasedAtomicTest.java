package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Mock;

public final class JMockMockControlStateBasedAtomicTest extends InstinctTestCase {
    public void testGetMockedObject() {
        final MockControl mockControl = new JMockMockControl(new Mock(Readable.class));
        final Object mock = mockControl.getMockedObject();
        assertTrue(mock instanceof Readable);
    }
}
