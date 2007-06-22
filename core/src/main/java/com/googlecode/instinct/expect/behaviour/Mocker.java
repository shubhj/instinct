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

package com.googlecode.instinct.expect.behaviour;

import com.googlecode.instinct.internal.expect.behaviour.JMock2Mockery;
import com.googlecode.instinct.internal.expect.behaviour.JMock2MockeryImpl;
import com.googlecode.instinct.internal.util.Suggest;

public final class Mocker {
    @Suggest("Use a DI container to get this.")
    private static final JMock2Mockery J_MOCK2_MOCKERY = new JMock2MockeryImpl();

    private Mocker() {
        throw new UnsupportedOperationException();
    }

    @Suggest("Rename to getJMock2Mockery()?")
    public static JMock2Mockery getMockery() {
        return J_MOCK2_MOCKERY;
    }

    // FIX: Add all the methods from mockery, to allow for manual mocking.
}
