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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class PrimitiveTypeBoxerImplAtomicTest extends InstinctTestCase {
    private PrimitiveTypeBoxer primitiveTypeBoxer;

    @Override
    public void setUpTestDoubles() {
    }

    @Override
    public void setUpSubject() {
        primitiveTypeBoxer = new PrimitiveTypeBoxerImpl();
    }

    public void testConformsToClassTraits() {
        checkClass(PrimitiveTypeBoxerImpl.class, PrimitiveTypeBoxer.class);
    }

    public void testBoxesPrimitiveClassesIntoTheirNonPrimitiveCounterparts() {
        checkBoxPrimitive(Boolean.TYPE, Boolean.class);
        checkBoxPrimitive(Byte.TYPE, Byte.class);
        checkBoxPrimitive(Character.TYPE, Character.class);
        checkBoxPrimitive(Short.TYPE, Short.class);
        checkBoxPrimitive(Integer.TYPE, Integer.class);
        checkBoxPrimitive(Long.TYPE, Long.class);
        checkBoxPrimitive(Float.TYPE, Float.class);
        checkBoxPrimitive(Double.TYPE, Double.class);
    }

    public void testDoesNotBoxNonPrimitiveTypes() {
        checkBoxPrimitive(String.class, String.class);
        checkBoxPrimitive(Class.class, Class.class);
    }

    private <T> void checkBoxPrimitive(final Class<T> toBox, final Class<T> expectedBoxed) {
        final Class<T> boxedType = primitiveTypeBoxer.boxPrimitiveType(toBox);
        expect.that(boxedType).isEqualTo(expectedBoxed);
    }
}
