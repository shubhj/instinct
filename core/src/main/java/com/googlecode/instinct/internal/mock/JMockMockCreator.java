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

import org.jmock.Mock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;

public final class JMockMockCreator implements MockCreator {
    public <T> TestDoubleControl createController(final Class<T> toMock) {
        final String roleName = mockNameFromClass(toMock);
        return toMock.isInterface() ? createInterfaceMock(toMock, roleName) : createConcreteMock(toMock, roleName);
    }

    public <T> TestDoubleControl createController(final Class<T> toMock, final String roleName) {
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
