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

package com.googlecode.instinct.internal.actor;

import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;

public final class DummyCreatorSlowTest extends InstinctTestCase {
    @Subject(implementation = DummyCreator.class) private SpecificationDoubleCreator dummyCreator;

    public void testCreatesDummiesForInterfaces() {
        final Iterable<?> iterable = dummyCreator.createDouble(Iterable.class, "iterable");
        expect.that(iterable).isNotNull();
    }

    public void testCreatesDummiesForClasses() {
        final List<?> list = dummyCreator.createDouble(ArrayList.class, "list");
        expect.that(list).isNotNull();
    }

    public void testCreatesDummiesClassLiterals() {
        final Class<?> cls = dummyCreator.createDouble(Class.class, "class");
        expect.that(cls).isNotNull();
    }

    public void testCreatesDummiesForFinalClasses() {
        final String string = dummyCreator.createDouble(String.class, "string");
        expect.that(string).isNotNull();
    }

    public void testCreatesDummiesForPrimitiveClasses() {
        final Integer integer = dummyCreator.createDouble(int.class, "int");
        expect.that(integer).isNotNull();
        final Boolean b = dummyCreator.createDouble(boolean.class, "boolean");
        expect.that(b).isNotNull();
    }

    public void testDummiesCreatedForInterfacesWillThrowExceptions() {
        final Iterable<?> iterable = dummyCreator.createDouble(Iterable.class, "iterable");
        expectException(IllegalInvocationException.class, new Runnable() {
            public void run() {
                iterable.iterator();
            }
        });
    }
}
