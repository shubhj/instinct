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
