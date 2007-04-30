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

package com.googlecode.instinct.internal.core;

import java.lang.reflect.Method;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class SpecificationMethodImplAtomicTest extends InstinctTestCase {
    private SpecificationMethod specificationMethod;
    private Method method;

    @Override
    public void setUpTestDoubles() {
        method = getClass().getMethods()[0];
    }

    @Override
    public void setUpSubject() {
        specificationMethod = new SpecificationMethodImpl(method);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodImpl.class, SpecificationMethod.class);
    }

    public void testReturnNameOfMethodPassedInConstructor() {
        assertEquals(method.getName(), specificationMethod.getName());
    }
}
