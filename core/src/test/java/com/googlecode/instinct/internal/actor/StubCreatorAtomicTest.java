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

package com.googlecode.instinct.internal.actor;

import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class StubCreatorAtomicTest extends InstinctTestCase {
    @Subject(implementation = StubCreator.class) private SpecificationDoubleCreator stubCreator;

    public void testConformsToClassTraits() {
        checkClass(StubCreator.class, SpecificationDoubleCreator.class);
    }

    // TODO Create a proxy that returns stubs for all method calls.
    public void testCreatesStubsForInterfaces() {
//        final Object specDouble = stubCreator.createDouble(AnInterface.class, "anInterface");
//        expect.that(specDouble).isNotNull();
    }

    public void testCreatesStubsForAbstractClasses() {
    }

    public void testCreatesStubsForFinalClasses() {
    }

    public void testCreatesStubsForEnums() {
    }

    public void testCreatesStubsForNonFinalClasses() {
    }

    public void testCreatesStubsForClassesWithConstructorArgs() {
    }

    // TODO Create a proxy that keeps the real object as the delegate, and for methods not constructor initialised, returns a stub (how do we tell?).
    public void testStubsClassesContainingMethodsThatReturnValuesThatAreNotInitialisedInTheConstructor() {
    }

    @SuppressWarnings({"ALL"})
    private static interface AnInterface {
    }

    @SuppressWarnings({"ALL"})
    private abstract static class AbstractClass {
    }

    @SuppressWarnings({"ALL"})
    private static enum AnEnum {
    }

    @SuppressWarnings({"ALL"})
    private static final class AFinalClass {
    }

    @SuppressWarnings({"ALL"})
    private static final class ClassWithConstructorArgs {
        private ClassWithConstructorArgs(AnEnum anEnum, AnInterface anInterface) {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class ClassWithMethodsNotInitialisedInConstructor {
        private String stringValueNotInitialisedInConstructor;

        public void setStringValueNotInitialisedInConstructor(String stringValueNotInitialisedInConstructor) {
            this.stringValueNotInitialisedInConstructor = stringValueNotInitialisedInConstructor;
        }

        public String getStringValueNotInitialisedInConstructor() {
            return stringValueNotInitialisedInConstructor;
        }
    }
}
