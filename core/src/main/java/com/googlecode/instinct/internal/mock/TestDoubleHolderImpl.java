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

import java.util.HashMap;
import java.util.Map;

public final class TestDoubleHolderImpl implements TestDoubleHolder {
    private final Map<Object, TestDoubleControl> mockToControlMap = new HashMap<Object, TestDoubleControl>();

    public void addControl(final TestDoubleControl control, final Object testDouble) {
        mockToControlMap.put(testDouble, control);
    }

    public TestDoubleControl getController(final Object testDouble) {
        return mockToControlMap.get(testDouble);
    }

    public Object getDouble(final TestDoubleControl control) {
        for (final Object mockedObject : mockToControlMap.keySet()) {
            if (mockToControlMap.get(mockedObject).equals(control)) {
                return mockedObject;
            }
        }
        throw new UnkownTestDoubleException("Unable to find test double for control " + control);
    }
}
