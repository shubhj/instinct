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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.MethodEquality.methodEqualsIgnoringDeclaringClass;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import fj.data.List;
import java.lang.reflect.Method;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AMethodEqualityUtility {
    @Specification
    public void doesNotRemoveDuplicatesWhenThereAreNone() {
        final List<Method> methods =
                List.<Method>nil().cons(getDeclaredMethod(SomeClass.class, "before")).cons(getDeclaredMethod(SomeClass.class, "spec"));
        final List<Method> noDuplicates = methods.nub(methodEqualsIgnoringDeclaringClass());
        expect.that(noDuplicates).isOfSize(2);
    }

    @Specification
    public void removesDuplicates() {
        final List<Method> methods =
                List.<Method>nil().cons(getDeclaredMethod(SomeClass.class, "before")).cons(getDeclaredMethod(SomeClass.class, "before"));
        final List<Method> noDuplicates = methods.nub(methodEqualsIgnoringDeclaringClass());
        expect.that(noDuplicates).isOfSize(1);
    }

    private static final class SomeClass {
        @BeforeSpecification
        public void before() {
        }

        @Specification
        public void spec() {
        }
    }
}
