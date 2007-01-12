package com.googlecode.instinct.mock;

import org.jmock.Mock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;

public final class JMockMockCreator implements MockCreator {
    public <T> MockControl createMockController(final Class<T> toMock) {
        final String roleName = mockNameFromClass(toMock);
        return toMock.isInterface() ? createInterfaceMock(toMock, roleName) : createConcreteMock(toMock, roleName);
    }

    public <T> MockControl createMockController(final Class<T> toMock, final String roleName) {
        return toMock.isInterface() ? createInterfaceMock(toMock, roleName) : createConcreteMock(toMock, roleName);
    }

    private <T> MockControl createInterfaceMock(final Class<T> toMock, final String roleName) {
        return new JMockMockControl(new Mock(toMock, roleName));
    }

    private <T> MockControl createConcreteMock(final Class<T> toMock, final String roleName) {
//        return new JMockMockControl(new Mock(new CGLIBCoreMock(toMock, roleName)));
        return new JMockMockControl(new Mock(new ConcreteClassMock(toMock, roleName)));
    }
}
