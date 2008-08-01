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

package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;

@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
public final class FieldValueSpecImplAtomicTest extends InstinctTestCase {
    private static final String NAME_1 = "x";
    private static final String NAME_2 = "y";
    private static final Object VALUE_1 = new Object();
    private static final Object VALUE_2 = new Object();
    private static final Object FIELD_VALUE_SPEC_1 = new FieldValueSpecImpl("field1", 77);
    private static final FieldValueSpec FIELD_VALUE_SPEC_2 = new FieldValueSpecImpl("field7", "Is absolute zero cold enough?");
    private static final Object FIELD_VALUE_SPEC_NULL = new FieldValueSpecImpl(NAME_1, null);
    private static final Object VALUE_CAN_BE_NULL = null;

    public void testConformsToClassTraits() {
        checkClassWithoutParamChecks(FieldValueSpecImpl.class, FieldValueSpec.class);
    }

    public void testRejectsNullNameInConstructor() {
        expectException(IllegalArgumentException.class, new Runnable() {
            public void run() {
                new FieldValueSpecImpl(null, VALUE_1);
            }
        });
    }

    public void testCreate() {
        checkCreate(NAME_1, VALUE_CAN_BE_NULL);
        checkCreate(NAME_1, VALUE_1);
        checkCreate(NAME_2, VALUE_2);
    }

    public void testEquals() {
        checkDifferentClasses();
        checkNames();
        checkSimpleValues();
    }

    public void testToString() {
        expect.that(FIELD_VALUE_SPEC_1).hasToString(equalTo("FieldValueSpecImpl[name=field1,value=77]"));
        expect.that(FIELD_VALUE_SPEC_2).hasToString(equalTo("FieldValueSpecImpl[name=field7,value=Is absolute zero cold enough?]"));
    }

    private void checkDifferentClasses() {
        expect.that(FIELD_VALUE_SPEC_1).isNotEqualTo(VALUE_1);
        expect.that(VALUE_1).isNotEqualTo(FIELD_VALUE_SPEC_1);
    }

    private void checkNames() {
        expect.that(FIELD_VALUE_SPEC_1).isEqualTo(FIELD_VALUE_SPEC_1);
        expect.that(FIELD_VALUE_SPEC_1).isEqualTo(FIELD_VALUE_SPEC_1);
        expect.that(FIELD_VALUE_SPEC_1).isNotEqualTo(FIELD_VALUE_SPEC_2);
        expect.that((Object) FIELD_VALUE_SPEC_2).isNotEqualTo(FIELD_VALUE_SPEC_1);
    }

    private void checkSimpleValues() {
        expect.that(FIELD_VALUE_SPEC_1).isEqualTo(FIELD_VALUE_SPEC_1);
        expect.that(FIELD_VALUE_SPEC_NULL).isEqualTo(FIELD_VALUE_SPEC_NULL);
        expect.that(FIELD_VALUE_SPEC_1).isNotEqualTo(FIELD_VALUE_SPEC_NULL);
        expect.that(FIELD_VALUE_SPEC_NULL).isNotEqualTo(FIELD_VALUE_SPEC_1);
    }

    private void checkCreate(final String name, final Object value) {
        final FieldValueSpec fieldValue = new FieldValueSpecImpl(name, value);
        expect.that(fieldValue.getName()).isEqualTo(name);
        expect.that(fieldValue.getValue()).isEqualTo(value);
    }
}
