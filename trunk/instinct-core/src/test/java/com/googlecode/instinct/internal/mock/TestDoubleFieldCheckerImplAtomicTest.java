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

import java.lang.reflect.Field;
import com.googlecode.instinct.core.TestDoubleConfigurationException;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getField;
import com.googlecode.instinct.internal.mock.TestDoubleFieldCheckerImpl;
import com.googlecode.instinct.internal.mock.TestDoubleFieldChecker;

@SuppressWarnings({"UnusedDeclaration", "FieldMayBeStatic"})
public final class TestDoubleFieldCheckerImplAtomicTest extends InstinctTestCase {
    private TestDoubleFieldChecker fieldChecker;
    private Object instance;

    public void testProperties() {
        checkClass(TestDoubleFieldCheckerImpl.class, TestDoubleFieldChecker.class);
    }

    public void testCheckFieldRejectsInvalidFields() {
        checkInvalidFieldThrowsException("invalidFinalField", "Field invalidFinalField cannot be final");
        checkInvalidFieldThrowsException("invalidFinalField2", "Field invalidFinalField2 cannot be final");
        checkInvalidFieldThrowsException("invalidNonNullField", "Field invalidNonNullField must be null");
        checkInvalidFieldThrowsException("invalidNonNullField2", "Field invalidNonNullField2 must be null");
    }

    public void testValidFieldIsANoOp() {
        final Field field = getField(ClassWithFields.class, "validField");
        fieldChecker.checkField(field, instance);
    }

    private void checkInvalidFieldThrowsException(final String fieldName, final String expectedMessage) {
        final Field field = getField(ClassWithFields.class, fieldName);
        assertThrows(TestDoubleConfigurationException.class, expectedMessage, new Runnable() {
            public void run() {
                fieldChecker.checkField(field, instance);
            }
        });
    }

    @Override
    public void setUpTestDoubles() {
        instance = new ClassWithFields();
    }

    @Override
    public void setUpSubject() {
        fieldChecker = new TestDoubleFieldCheckerImpl();
    }

    private static final class ClassWithFields {
        private final String invalidFinalField = "";
        private final String invalidFinalField2 = "";
        private String invalidNonNullField = "";
        private String invalidNonNullField2 = "";
        private String validField;
    }
}
