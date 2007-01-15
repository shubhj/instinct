package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;
import com.googlecode.instinct.core.TestDoubleConfigurationException;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getField;

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

    private static class ClassWithFields {
        private final String invalidFinalField = "";
        private final String invalidFinalField2 = "";
        private String invalidNonNullField = "";
        private String invalidNonNullField2 = "";
        private String validField;
    }
}
