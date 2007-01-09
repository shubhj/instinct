package com.googlecode.instinct.test;

import junit.framework.TestCase;
import org.jmock.Mock;
import org.jmock.cglib.CGLIBCoreMock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;

@SuppressWarnings({"ProhibitedExceptionDeclared", "NoopMethodInAbstractClass", "unchecked"})
public abstract class InstinctTestCase extends TestCase {
    @Override
    public final void setUp() throws Exception {
        setUpMocks();
        setUpSubjects();
    }

    public <T> T mock(final Class<T> toMock) {
        return toMock.isInterface() ? (T) new Mock(toMock).proxy() : (T) new CGLIBCoreMock(toMock, mockNameFromClass(toMock));
    }

    public void setUpMocks() {
    }

    public void setUpSubjects() {
    }
}
