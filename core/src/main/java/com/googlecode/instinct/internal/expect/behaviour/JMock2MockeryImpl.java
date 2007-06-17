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

package com.googlecode.instinct.internal.expect.behaviour;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.jmock.Mockery;
import org.jmock.internal.ExpectationBuilder;
import org.jmock.lib.legacy.ClassImposteriser;

public final class JMock2MockeryImpl implements JMock2Mockery {
    @SuppressWarnings({"EmptyClass"})
    private final Mockery mockery = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
//            setExpectationErrorTranslator(new IdentityExpectationErrorTranslator());
        }
    };

    public <T> T mock(final Class<T> typeToMock) {
        checkNotNull(typeToMock);
        return mockery.mock(typeToMock);
    }

    public <T> T mock(final Class<T> typeToMock, final String roleName) {
        checkNotNull(typeToMock, roleName);
        return mockery.mock(typeToMock, roleName);
    }

    public void checking(final ExpectationBuilder expectations) {
        checkNotNull(expectations);
        mockery.checking(expectations);
    }

    public void verify() {
        mockery.assertIsSatisfied();
    }
}
