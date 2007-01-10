package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Mock;
import org.jmock.builder.InvocationMockerBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.matcher.AnyArgumentsMatcher;

public final class JMockMockControlAtomicTest extends InstinctTestCase {
    public void testGetMockedObject() {
        final MockControl mockControl = new JMockMockControl(new Mock(Readable.class));
        final Object mock = mockControl.getMockedObject();
        assertNotNull(mock);
        assertTrue(mock instanceof Readable);
    }

    public void testExpects() {
        final MockControl mockControl = new JMockMockControl(new Mock(Readable.class));
        final NameMatchBuilder matchBuilder = mockControl.expects(new AnyArgumentsMatcher());
        assertNotNull(matchBuilder);
        assertTrue(matchBuilder instanceof InvocationMockerBuilder);
    }
}
