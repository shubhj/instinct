package com.googlecode.instinct.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import junit.framework.AssertionFailedError;
import org.jmock.Mock;
import org.jmock.core.matcher.InvokeOnceMatcher;

@SuppressWarnings({"ErrorNotRethrown"})
public final class JMockMockControlInteractionBasedAtomicTest extends InstinctTestCase {


    public void testGetMockedObject() {
    }

    public void testExpects() {
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
