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

package com.googlecode.instinct.test;

import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.marker.annotate.Mock;
import java.lang.reflect.Field;

public final class AutoMocker {
    private AutoMocker() {
    }

    public static <T> void setUpAutoMocks(final T objectToMock) throws IllegalAccessException {
        final Field[] fields = objectToMock.getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (field.getAnnotation(Mock.class) != null) {
                field.setAccessible(true);
                injectMockIntoField(objectToMock, field);
            }
        }
    }

    private static <T> void injectMockIntoField(final T objectToMock, final Field field) throws IllegalAccessException {
        field.set(objectToMock, mock(field.getType()));
    }
}
