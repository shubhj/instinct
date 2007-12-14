/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.actor;

import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.reflect.Array;

public final class MockCreator implements SpecificationDoubleCreator {
    @SuppressWarnings({"CatchGenericClass"})
    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        checkNotNull(doubleType, roleName);
        try {
            return doCreateDouble(doubleType, roleName);
        } catch (Throwable e) {
            final String message = "Unable to create a mock " + doubleType.getName() + " (with role name '" + roleName
                    + "'). Mock types cannot be final, you may want to use a dummy or a stub.";
            throw new SpecificationDoubleCreationException(message, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    private <T> T doCreateDouble(final Class<T> doubleType, final String roleName) {
        if (doubleType.isArray()) {
            return (T) createArray(doubleType.getComponentType(), roleName);
        } else {
            return mock(doubleType, roleName);
        }
    }

    @SuppressWarnings({"StringContatenationInLoop", "unchecked"})
    private <E> Object createArray(final Class<E> componentType, final String fieldName) {
        final Object array = Array.newInstance(componentType, NUMBER_OF_DOUBLES_IN_AN_ARRAY);
        for (int i = 0; i < NUMBER_OF_DOUBLES_IN_AN_ARRAY; i++) {
            Array.set(array, i, mock(componentType, fieldName + "-" + i));
        }
        return array;
    }
}
