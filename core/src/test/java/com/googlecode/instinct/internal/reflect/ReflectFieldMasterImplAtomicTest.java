/*
 * Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.reflect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.lang.FieldValueSpec;
import com.googlecode.instinct.internal.lang.FieldValueSpecImpl;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.Map;

public final class ReflectFieldMasterImplAtomicTest extends InstinctTestCase {
    private static final String FIELD_VALUE_1 = "REFACTORING IS THE MOST IMPORTANT THING YOU COULD BE DOING";
    private static final String FIELD_VALUE_2 = "TEAR DOWN THE BARRIERS TO ADHOC PAIRING";
    private static final Object WRAPPED_INTEGER_1 = 1;
    @Subject(implementation = ReflectFieldMasterImpl.class) private ReflectFieldMaster master;

    public void testConformsToClassTraits() {
        checkClass(ConstructorInvokerImpl.class, ConstructorInvoker.class);
    }

    public void testFields() {
        checkFields(new FieldValueSpec[0], new TestZeroInstanceFieldsObject());
        checkFields(new FieldValueSpec[0], new TestZeroInstanceOneStaticFieldsObject());
        checkFields(createFieldSpec(FIELD_VALUE_1), new TestOneInstanceFieldObject(FIELD_VALUE_1));
        checkFields(createFieldSpec(FIELD_VALUE_2), new TestOneInstanceFieldObject(FIELD_VALUE_2));
        checkFields(createFieldSpec(WRAPPED_INTEGER_1), new TestOnePrimitiveInstanceFieldObject(1));
    }

    private void checkFields(final FieldValueSpec[] expectedFields, final Object o) {
        final FieldValueSpec[] actualFields = master.getInstanceFields(o);
        expect.that(actualFields).isEqualTo(expectedFields);
    }

    private static FieldValueSpec[] createFieldSpec(final Object value) {
        return new FieldValueSpec[]{new FieldValueSpecImpl("value", value)};
    }

    @SuppressWarnings({"ALL"})
    private interface TestInterfaceOne {
        void fridayIsHere();

        Integer getSmeetOthEchuRchontIme(String frankyfurter, Map beanTypes);
    }

    @SuppressWarnings({"ALL"})
    private interface TestInterfaceTwo {
        void justSomeOldMethod();
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOneInstanceFieldObject {
        private String value = "PAIR OR QUAD";

        TestOneInstanceFieldObject(String value) {
            this.value = value;
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOnePrimitiveInstanceFieldObject {
        private final int value;

        public TestOnePrimitiveInstanceFieldObject(int i) {
            this.value = i;
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOneConstructor {
        public TestOneConstructor() {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOnePrivateConstructor {
        private TestOnePrivateConstructor() {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestTwoConstructors {
        private TestTwoConstructors() {
        }

        private TestTwoConstructors(String aString) {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestThreeConstructors {
        private TestThreeConstructors() {
        }

        private TestThreeConstructors(String aString) {
        }

        private TestThreeConstructors(int anInt) {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestZeroInstanceFieldsObject {
    }

    @SuppressWarnings({"ALL"})
    private static final class TestZeroInstanceOneStaticFieldsObject {
        private static final String STATIC_FIELD_1 = "YO MAMA";
    }
}
