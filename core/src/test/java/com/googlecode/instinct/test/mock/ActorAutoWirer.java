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

import com.googlecode.instinct.internal.testdouble.DummyCreator;
import com.googlecode.instinct.internal.testdouble.SpecificationDoubleCreator;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.TestingException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

@SuppressWarnings({"CatchGenericClass"})
public final class ActorAutoWirer {
    private static final SubjectCreator SUBJECT_CREATOR = new SubjectCreatorImpl();
    private static final SpecificationDoubleCreator DUMMY_CREATOR = new DummyCreator();
    private static final SpecificationDoubleCreator STUB_CREATOR = new StubCreator();
    private static final SpecificationDoubleCreator MOCK_CREATOR = new MockCreator();

    private ActorAutoWirer() {
        throw new UnsupportedOperationException();
    }

    public static void autoWireMockFields(final Object instanceToAutoWire) {
        final Field[] fields = instanceToAutoWire.getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Mock.class) && autoWireMock(field)) {
                injectMock(instanceToAutoWire, field);
            } else if (field.isAnnotationPresent(Stub.class) && autoWireStub(field)) {
                injectStub(instanceToAutoWire, field);
            } else if (field.isAnnotationPresent(Dummy.class) && autoWireDummy(field)) {
                injectDummy(instanceToAutoWire, field);
            }
        }
    }

    public static void autoWireSubjectFields(final Object instanceToAutoWire) {
        final Field[] fields = instanceToAutoWire.getClass().getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Subject.class) && autoWireSubject(field)) {
                injectSubject(instanceToAutoWire, field);
            }
        }
    }

    private static void injectMock(final Object instanceToAutoWire, final Field field) {
        injectFieldValue(instanceToAutoWire, field, MOCK_CREATOR.createDouble(field.getType(), field.getName()), "mock");
    }

    private static void injectDummy(final Object instanceToAutoWire, final Field field) {
        injectFieldValue(instanceToAutoWire, field, DUMMY_CREATOR.createDouble(field.getType(), field.getName()), "dummy");
    }

    private static void injectStub(final Object instanceToAutoWire, final Field field) {
        injectFieldValue(instanceToAutoWire, field, STUB_CREATOR.createDouble(field.getType(), field.getName()), "dummy");
    }

    private static void injectSubject(final Object instanceToAutoWire, final Field field) {
        injectFieldValue(instanceToAutoWire, field, SUBJECT_CREATOR.create(field), "subject");
    }

    private static void injectFieldValue(final Object instanceToAutoWire, final Field field, final Object fieldValue, final String testDoubleType) {
        field.setAccessible(true);
        try {
            field.set(instanceToAutoWire, fieldValue);
        } catch (Throwable throwable) {
            final String message = "Unable to autowire a " + testDoubleType + " value into field '" + field.getName() + "' of type " +
                    field.getType().getSimpleName();
            throw new TestingException(message, throwable);
        }
    }

    private static boolean autoWireSubject(final AnnotatedElement subjectField) {
        final Subject annotation = subjectField.getAnnotation(Subject.class);
        return annotation.auto();
    }

    private static boolean autoWireMock(final AnnotatedElement mockField) {
        final Mock annotation = mockField.getAnnotation(Mock.class);
        return annotation.auto();
    }

    private static boolean autoWireStub(final AnnotatedElement stubField) {
        final Stub annotation = stubField.getAnnotation(Stub.class);
        return annotation.auto();
    }

    private static boolean autoWireDummy(final AnnotatedElement dummyField) {
        final Dummy annotation = dummyField.getAnnotation(Dummy.class);
        return annotation.auto();
    }
}
