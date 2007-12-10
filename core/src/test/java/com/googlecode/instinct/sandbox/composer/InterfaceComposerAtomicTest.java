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

package com.googlecode.instinct.sandbox.composer;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectMessageContains;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;
import org.jmock.Expectations;

@SuppressWarnings({"InstanceVariableOfConcreteClass", "QuestionableName", "ProtectedField", "ProhibitedExceptionDeclared"})
public final class InterfaceComposerAtomicTest extends InstinctTestCase {
    @Subject(implementation = InterfaceComposerImpl.class) private InterfaceComposer interfaceComposer;
    @Mock private ComposedInterface composedInterface;
    @Stub private One one;
    @Stub private Two two;
    @Stub private Three three;
    @Stub(auto = false) private ComposedInterface oneAndTwo;
    @Stub(auto = false) private ComposedInterface oneTwoAndThree;
    @Stub(auto = false) private ComposedInterface backedByMockImplementation;
    @Stub private Throwable throwable;

    @Override
    public void setUpSubject() {
        oneAndTwo = interfaceComposer.compose(ComposedInterface.class, one, two);
        oneTwoAndThree = interfaceComposer.compose(ComposedInterface.class, one, two, three);
        backedByMockImplementation = interfaceComposer.compose(ComposedInterface.class, composedInterface);
    }

    public void testDelgatesToMethodWithMostSpecificReturnType() {
        expect.that(oneAndTwo.returnANumber()).equalTo(one.returnANumber());
        expect.that(oneAndTwo.returnAnotherNumber()).equalTo(two.returnAnotherNumber());
        expect.that(oneAndTwo.getObject()).equalTo(two.getObject());
        expect.that(oneTwoAndThree.getObject()).equalTo(three.getObject());
    }

    public void testThrowsNoSuchMethodExceptionWhenMethodNotFound() {
        final Throwable throwable = expectException(NoSuchMethodError.class, new Runnable() {
            public void run() {
                oneAndTwo.notImplemented();
            }
        });
        expectMessageContains(throwable, "No implementation found for: ");
    }

    public void testReThrowsErrors() {
        checkRethrowsSameThrowable(new Error());
    }

    public void testReThrowsRuntimeExceptions() {
        checkRethrowsSameThrowable(new RuntimeException());
    }

    public void testWrapsOtherThrowables() throws Throwable {
        expect.that(new Expectations() {
            {
                one(composedInterface).throwSomething();
                will(throwException(throwable));
            }
        });
        try {
            backedByMockImplementation.throwSomething();
        } catch (Throwable actualThrowable) {
            expect.that(actualThrowable).isOfType(RuntimeException.class);
            expect.that(actualThrowable.getCause()).sameInstanceAs(throwable);
        }
    }

    private void checkRethrowsSameThrowable(final Throwable expectedThrowable) {
        expect.that(new Expectations() {
            {
                one(composedInterface).returnANumber();
                will(throwException(expectedThrowable));
            }
        });
        final Throwable actualThrowable = expectException(expectedThrowable.getClass(), new Runnable() {
            public void run() {
                composedInterface.returnANumber();
            }
        });
        expect.that(actualThrowable).sameInstanceAs(expectedThrowable);
    }

    private static class One {
        private final int number = getInstance(Integer.class);
        private final Object object = getInstance(String.class);

        public int returnANumber() {
            return number;
        }

        public Object getObject() {
            return object;
        }
    }

    private static class Two {
        protected final String object = getInstance(String.class);
        private final int number = getInstance(Integer.class);

        public int returnAnotherNumber() {
            return number;
        }

        public String getObject() {
            return object;
        }
    }

    private static class Three extends Two {
        @Override
        public String getObject() {
            return object;
        }
    }

    private interface ComposedInterface {
        int returnANumber();

        int returnAnotherNumber();

        Object getObject();

        void notImplemented();

        void throwSomething() throws Throwable;
    }
}