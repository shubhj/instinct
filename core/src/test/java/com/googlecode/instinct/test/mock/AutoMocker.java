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

package com.googlecode.instinct.test.mock;

import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.test.TestingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public final class AutoMocker {
    private static final int NUMBER_OF_MOCKS_IN_A_COLLECTION_FIELD = 3;

    private AutoMocker() {
        throw new UnsupportedOperationException();
    }

    @Suggest("Add other test doubles here.")
    public static void autoWireMockFields(final Object instanceToAutoWire) {
        final Field[] fields = instanceToAutoWire.getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (isAnnotated(Mock.class, field)) {
                injectMock(instanceToAutoWire, field);
            }
            // note. other test doubles here.
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    private static void injectMock(final Object instanceToAutoWire, final Field field) {
        field.setAccessible(true);
        try {
            field.set(instanceToAutoWire, createFieldValue(field.getType(), field.getName()));
        } catch (Throwable e) {
            final String message = "Unable to autowire a mock value into field '" + field.getName() + "' of type " + field.getType().getSimpleName();
            throw new TestingException(message, e);
        }
    }

    private static Object createFieldValue(final Class<?> fieldType, final String fieldName) {
        if (fieldType.isArray()) {
            return createArray(fieldType.getComponentType(), fieldName);
        } else {
            return mock(fieldType, fieldName);
        }
    }

    @SuppressWarnings({"StringContatenationInLoop"})
    private static <T> Object createArray(final Class<?> componentType, final String fieldName) {
        final Object array = Array.newInstance(componentType, NUMBER_OF_MOCKS_IN_A_COLLECTION_FIELD);
        for (int i = 0; i < NUMBER_OF_MOCKS_IN_A_COLLECTION_FIELD; i++) {
            Array.set(array, i, mock(componentType, fieldName + "-" + i));
        }
        return array;
    }

    private static <T extends Annotation> boolean isAnnotated(final Class<T> annotation, final Field field) {
        return field.getAnnotation(annotation) != null;
    }
}
