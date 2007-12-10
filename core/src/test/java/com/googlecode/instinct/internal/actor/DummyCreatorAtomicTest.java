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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.internal.actor.SpecificationDoubleCreator.NUMBER_OF_DOUBLES_IN_AN_ARRAY;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Note. Cannot use auto-wired dummies here as we're testing the dummy creator.
public final class DummyCreatorAtomicTest extends InstinctTestCase {
    @Subject(implementation = DummyCreator.class) private SpecificationDoubleCreator dummyCreator;

    public void testConformsToClassTraits() {
        checkClass(DummyCreator.class, SpecificationDoubleCreator.class);
    }

    public void testWillNotCreateDummiesForFinalClasses() {
        final String message = "Unable to create a dummy " + AFinalClass.class.getName() +
                " (with role name 'aFinalClass'). Dummy types cannot be primitives, final classes or enums, use a stub for these.";
        expectException(SpecificationDoubleCreationException.class, message, new Runnable() {
            public void run() {
                dummyCreator.createDouble(AFinalClass.class, "aFinalClass");
            }
        });
    }

    public void testWillNotCreateDummiesForEnums() {
        final String message = "Unable to create a dummy " + AnEnum.class.getName() +
                " (with role name 'anEnum'). Dummy types cannot be primitives, final classes or enums, use a stub for these.";
        expectException(SpecificationDoubleCreationException.class, message, new Runnable() {
            public void run() {
                dummyCreator.createDouble(AnEnum.class, "anEnum");
            }
        });
    }

    public void testWillNotCreateDummiesForPrimitives() {
        final String message = "Unable to create a dummy " + int.class.getName() +
                " (with role name 'int'). Dummy types cannot be primitives, final classes or enums, use a stub for these.";
        expectException(SpecificationDoubleCreationException.class, message, new Runnable() {
            public void run() {
                dummyCreator.createDouble(int.class, "int");
            }
        });
    }

    public void testCreatesDummiesForArraysByFillingTheArrayWithDummiesOfTheComponentType() {
        final Iterable[] iterables = {};
        Iterable[] dummyIterables = dummyCreator.createDouble(iterables.getClass(), "iterables");
        expect.that(dummyIterables).isOfSize(NUMBER_OF_DOUBLES_IN_AN_ARRAY);
    }

    public void testWillNotCreateADummyArrayWhenTheComponentTypeCannotBeDummied() {
        final String[] strings = {};
        final String message = "Unable to create a dummy " + String.class.getName() + "[] (with role name 'strings') as the component " +
                "type is not itself dummiable. The component type cannot be a primitive, final class or enum, use a stub for these.";
        expectException(SpecificationDoubleCreationException.class, message, new Runnable() {
            public void run() {
                dummyCreator.createDouble(strings.getClass(), "strings");
            }
        });
    }

    public void testCanDummyAbstractClasses() {
        dummyCreator.createDouble(AnAbstractClass.class, "anAbstractClass");
    }

    public void testCreatesDummiesForNonFinalClasses() {
        final List<?> list = dummyCreator.createDouble(ArrayList.class, "list");
        expect.that(list).isNotNull();
    }

    public void testCreatesDummiesForAnnotations() {
        final AnAnnotation list = dummyCreator.createDouble(AnAnnotation.class, "list");
        expect.that(list).isNotNull();
    }

    public void testCreatesDummiesForInterfaces() {
        final Iterable<?> iterable = dummyCreator.createDouble(Iterable.class, "iterable");
        expect.that(iterable).isNotNull();
    }

    public void testDummiesCreatedForInterfacesWillThrowExceptions() {
        final Iterable<?> iterable = dummyCreator.createDouble(Iterable.class, "iterable");
        expectException(IllegalInvocationException.class, new Runnable() {
            public void run() {
                iterable.iterator();
            }
        });
    }

    // Note. Exposes a closed bug in proxy generation.
    public void testCanDummyFiles() {
        dummyCreator.createDouble(File.class, "file");
    }

    @SuppressWarnings({"ALL"})
    private static enum AnEnum {
    }

    @SuppressWarnings({"ALL"})
    private static final class AFinalClass {
    }

    @SuppressWarnings({"ALL"})
    private static abstract class AnAbstractClass {
    }

    @SuppressWarnings({"ALL"})
    private static @interface AnAnnotation {
    }
}
