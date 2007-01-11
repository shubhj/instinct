package com.googlecode.instinct.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import junit.framework.AssertionFailedError;
import org.jmock.Mock;
import org.jmock.builder.InvocationMockerBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.matcher.AnyArgumentsMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;

@SuppressWarnings({"ErrorNotRethrown"})
public final class JMockMockControlStateBasedAtomicTest extends InstinctTestCase {
    public void testGetMockedObject() {
        final Object mock = createControl().getMockedObject();
        assertNotNull(mock);
        assertTrue(mock instanceof Readable);
    }

    public void testExpects() {
        final NameMatchBuilder matchBuilder = createControl().expects(new AnyArgumentsMatcher());
        assertNotNull(matchBuilder);
        assertTrue(matchBuilder instanceof InvocationMockerBuilder);
    }

    public void testVerify() {
        try {
            final Mock mock = createMock();
            mock.expects(new InvokeOnceMatcher()).method("read");
            createControl(mock).verify();
            fail();
        } catch (AssertionFailedError expected) {
        }
    }

    public void testToString() {
        assertEquals("mockReadable", createControl().toString());
    }

    private MockControl createControl() {
        return createControl(createMock());
    }

    private MockControl createControl(final Mock mock) {
        return new JMockMockControl(mock);
    }

    private Mock createMock() {
        return new Mock(Readable.class);
    }
}
