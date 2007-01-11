package com.googlecode.instinct.mock;

import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Mock;
import org.jmock.builder.InvocationMockerBuilder;
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.matcher.AnyArgumentsMatcher;

public final class JMockMockControlAtomicTest extends InstinctTestCase {
    public void testGetMockedObject() {
        final Object mock = createControl().getMockedObject();
        checkValue(mock, Readable.class);
    }

    public void testExpects() {
        final NameMatchBuilder matchBuilder = createControl().expects(new AnyArgumentsMatcher());
        checkValue(matchBuilder, InvocationMockerBuilder.class);
    }

    private MockControl createControl() {
        return new JMockMockControl(new Mock(Readable.class));
    }

    private <T> void checkValue(final Object toCheck, final Class<T> expectedClass) {
        assertNotNull(toCheck);
        assertTrue(expectedClass.isAssignableFrom(toCheck.getClass()));
    }
}
