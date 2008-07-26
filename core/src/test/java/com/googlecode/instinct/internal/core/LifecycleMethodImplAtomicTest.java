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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Method;

public final class LifecycleMethodImplAtomicTest extends InstinctTestCase {
    private LifecycleMethod lifecycleMethod;
    private Method method;

    @Override
    public void setUpTestDoubles() {
        method = SampleClass.class.getMethods()[0];
    }

    @Override
    public void setUpSubject() {
        lifecycleMethod = new LifecycleMethodImpl(SampleClass.class, method);
    }

    public void testConformsToClassTraits() {
        checkClass(LifecycleMethodImpl.class, LifecycleMethod.class);
    }

    public void testReturnNameOfMethodPassedInConstructor() {
        expect.that(lifecycleMethod.getName()).isEqualTo(method.getName());
    }

    public void testReturnsUnderlyingMethod() {
        expect.that(lifecycleMethod.getMethod()).isTheSameInstanceAs(method);
    }

    public void testReturnsDeclaringClassOfUnderlyingMethod() {
        expect.that((Object) lifecycleMethod.getContextClass()).isTheSameInstanceAs(SampleClass.class);
    }

    private static final class SampleClass {
        @Specification
        public void someMethod() {
        }
    }
}
