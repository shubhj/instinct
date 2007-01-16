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
import org.jmock.builder.NameMatchBuilder;
import org.jmock.core.InvocationMatcher;

public final class JMockMockControl implements MockControl {
    private final Mock mockController;

    public JMockMockControl(final Mock mockController) {
        this.mockController = mockController;
    }

    public Object createTestDouble() {
        return mockController.proxy();
    }

    public NameMatchBuilder expects(final InvocationMatcher expectation) {
        return mockController.expects(expectation);
    }

    public void verify() {
        mockController.verify();
    }

    @Override
    public String toString() {
        return mockController.toString();
    }
}
