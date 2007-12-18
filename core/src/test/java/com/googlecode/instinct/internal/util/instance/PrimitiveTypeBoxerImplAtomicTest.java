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

package com.googlecode.instinct.internal.util.instance;

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
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Boolean.TYPE)).isEqualTo(Boolean.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Byte.TYPE)).isEqualTo(Byte.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Character.TYPE)).isEqualTo(Character.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Short.TYPE)).isEqualTo(Short.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Integer.TYPE)).isEqualTo(Integer.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Long.TYPE)).isEqualTo(Long.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Float.TYPE)).isEqualTo(Float.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Double.TYPE)).isEqualTo(Double.class);
    }

    public void testDoesNotBoxNonPrimitiveTypes() {
        expect.that(primitiveTypeBoxer.boxPrimitiveType(String.class)).isEqualTo(String.class);
        expect.that(primitiveTypeBoxer.boxPrimitiveType(Class.class)).isEqualTo(Class.class);
    }
}
