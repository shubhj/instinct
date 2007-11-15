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

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectMessageContains;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;
import org.jmock.Expectations;
import org.jmock.Mockery;

@SuppressWarnings({"InstanceVariableOfConcreteClass", "QuestionableName", "ProtectedField", "ProhibitedExceptionDeclared"})
public class InterfaceComposerAtomicTest extends InstinctTestCase {
    private Mockery context = new Mockery();
    private One one = new One();
    private Two two = new Two();
    private Three three = new Three();
    private ComposedInterface mockComposedInterface = context.mock(ComposedInterface.class);
    private ComposedInterface oneAndTwo;
    private ComposedInterface oneTwoAndThree;
    private ComposedInterface backedByMockImplementation;

    public void testConformsToClassTraits() {
        // TODO
    }

    public void testDelgatesToMethodWithMostSpecificReturnType() {
        assertEquals(one.returnANumber(), oneAndTwo.returnANumber());
        assertEquals(two.returnAnotherNumber(), oneAndTwo.returnAnotherNumber());
        assertEquals(two.getObject(), oneAndTwo.getObject());
        assertEquals(three.getObject(), oneTwoAndThree.getObject());
    }

    public void testThrowsNoSuchMethodExceptionWhenMethodNotFound() {
        final Throwable throwable = expectException(NoSuchMethodError.class,
                new Runnable() {
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
        final Throwable expectedThrowable = new Throwable();
        context.checking(new Expectations() {
            {
                one(mockComposedInterface).throwSomething();
                will(throwException(expectedThrowable));
            }
        });
        try {
            backedByMockImplementation.throwSomething();
        } catch (Throwable actualThrowable) {
            context.assertIsSatisfied();
            assertSame(RuntimeException.class, actualThrowable.getClass());
            assertSame(expectedThrowable, actualThrowable.getCause());
        }
    }

    @Override
    public void setUpTestDoubles() {
    }

    @Override
    public void setUpSubject() {
        final InterfaceComposer interfaceComposer = new InterfaceComposerImpl();
        oneAndTwo = interfaceComposer.compose(ComposedInterface.class, one, two);
        oneTwoAndThree = interfaceComposer.compose(ComposedInterface.class, one, two, three);
        backedByMockImplementation = interfaceComposer.compose(ComposedInterface.class, mockComposedInterface);
    }

    private void checkRethrowsSameThrowable(final Throwable expectedThrowable) {
        final ComposedInterface mockComposedInterface = context.mock(ComposedInterface.class);
        context.checking(new Expectations() {
            {
                one(mockComposedInterface).returnANumber();
                will(throwException(expectedThrowable));
            }
        });
        final Throwable actualThrowable = expectException(expectedThrowable.getClass(),
                new Runnable() {
                    public void run() {
                        mockComposedInterface.returnANumber();
                    }
                });
        context.assertIsSatisfied();
        assertSame(expectedThrowable, actualThrowable);
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