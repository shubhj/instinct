package com.googlecode.instinct.mock;

import org.jmock.Mock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;

public final class JMockMockCreator implements MockCreator {
    public <T> MockControl createMockController(final Class<T> toMock) {
        return toMock.isInterface() ? createInterfaceMock(toMock) : createConcreteMock(toMock);
    }

    private <T> MockControl createInterfaceMock(final Class<T> toMock) {
        return new JMockMockControl(new Mock(toMock));
    }

    private <T> MockControl createConcreteMock(final Class<T> toMock) {
//        return new JMockMockControl(new Mock(new CGLIBCoreMock(toMock, mockNameFromClass(toMock))));
        return new JMockMockControl(new Mock(new ConcreteClassMock(toMock, mockNameFromClass(toMock))));
    }
}
