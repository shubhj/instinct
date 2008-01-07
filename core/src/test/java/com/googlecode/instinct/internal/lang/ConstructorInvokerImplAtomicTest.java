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

package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;

public final class ConstructorInvokerImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = ConstructorInvokerImpl.class) ConstructorInvoker invoker;

    public void testWillNotInvokeTheConstructorOfAnAbstractClass() {
        final String message = "Cannot instantiate the constructor of an abstract class: " + AnAbstractClass.class.getName();
        expectException(RuntimeException.class, message, new Runnable() {
            public void run() {
                invoker.invokeNullaryConstructor(AnAbstractClass.class);
            }
        });
    }

    public void testWillInvokeTheNullaryConstructorOfANonAbstractClass() {
        final Object instantiatedObject = invoker.invokeNullaryConstructor(Object.class);
        expect.that(instantiatedObject).isNotNull();
    }

    public void testWillNotInvokeTheConstructorOfAClassWithOutANullaryConstructor() {
        final String message =
                "Unable to instantiate " + NoNullaryConstructorClass.class.getName() + " as it does not have a nullary (no-args) constructor";
        expectException(RuntimeException.class, message, new Runnable() {
            public void run() {
                invoker.invokeNullaryConstructor(NoNullaryConstructorClass.class);
            }
        });
    }

    @SuppressWarnings({"ALL"})
    private abstract static class AnAbstractClass {
    }

    @SuppressWarnings({"ALL"})
    private final static class NoNullaryConstructorClass {
        private NoNullaryConstructorClass(String whoCares) {
        }
    }
}
