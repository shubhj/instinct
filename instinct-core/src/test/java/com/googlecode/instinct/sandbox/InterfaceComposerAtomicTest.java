/*
 * Copyright 2006-2007 Ben Warren
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

package com.googlecode.instinct.sandbox;

import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertMessageContains;
import static com.googlecode.instinct.test.triangulate.Triangulation.*;
import com.googlecode.instinct.test.InstinctTestCase;
import org.jmock.Mockery;
import org.jmock.Expectations;

public class InterfaceComposerAtomicTest extends InstinctTestCase {

    private ComposedInterface oneAndTwo;
    private ComposedInterface oneTwoAndThree;
    private InterfaceComposerAtomicTest.One one = new One();
    private InterfaceComposerAtomicTest.Two two = new Two();
    private InterfaceComposerAtomicTest.Three three = new Three();
    private Mockery context = new Mockery();
    final ComposedInterface mockComposedInterface = context.mock(ComposedInterface.class);
    private ComposedInterface backedByMockImplementation;

    public void testProperties() {
        // TODO
    }

    public void testDelgatesToMethodWithMostSpecificReturnType() throws Exception {
        assertEquals(one.returnANumber(), oneAndTwo.returnANumber());
        assertEquals(two.returnAnotherNumber(), oneAndTwo.returnAnotherNumber());
        assertEquals(two.getObject(), oneAndTwo.getObject());
        assertEquals(three.getObject(), oneTwoAndThree.getObject());
    }

    public void testThrowsNoSuchMethodExceptionWhenMethodNotFound() {
        Throwable throwable = assertThrows(NoSuchMethodError.class, new Runnable() {
            public void run() {
                oneAndTwo.notImplemented();
            }
        });
        assertMessageContains(throwable, "No implementation found for: ");
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
                one(mockComposedInterface).thowSomething();
                will(throwException(expectedThrowable));
            }
        });

        try {
            backedByMockImplementation.thowSomething();
        } catch (Throwable actualThrowable) {
            context.assertIsSatisfied();
            assertSame(RuntimeException.class, actualThrowable.getClass());
            assertSame(expectedThrowable, actualThrowable.getCause());
        }
    }

    private void checkRethrowsSameThrowable(final Throwable expectedThrowable) {
        final ComposedInterface mockComposedInterface = context.mock(ComposedInterface.class);

        context.checking(new Expectations() {
            {
                one(mockComposedInterface).returnANumber();
                will(throwException(expectedThrowable));
            }
        });

        Throwable actualThrowable = assertThrows(expectedThrowable.getClass(), new Runnable() {
            public void run() {
                mockComposedInterface.returnANumber();
            }
        });

        context.assertIsSatisfied();
        assertSame(expectedThrowable, actualThrowable);
    }


    @Override
    public void setUpTestDoubles() {

    }

    @Override
    public void setUpSubject() {
        InterfaceComposer interfaceComposer = new InterfaceComposer();
        oneAndTwo = interfaceComposer.compose(ComposedInterface.class, one, two);
        oneTwoAndThree = interfaceComposer.compose(ComposedInterface.class, one, two, three);
        backedByMockImplementation = interfaceComposer.compose(ComposedInterface.class, mockComposedInterface);
    }

    public interface ComposedInterface {
        int returnANumber();

        int returnAnotherNumber();

        Object getObject();

        void notImplemented();

        void thowSomething() throws Throwable;
    }

    class One {

        private final int number = getInstance(Integer.class);
        private final Object object = getInstance(String.class);

        public int returnANumber() {
            return number;
        }

        public Object getObject() {
            return object;
        }
    }

    class Two {

        final int number = getInstance(Integer.class);
        final String object = getInstance(String.class);

        public int returnAnotherNumber() {
            return number;
        }

        public String getObject() {
            return object;
        }
    }

    class Three extends Two {

        public String getObject() {
            return object;
        }
    }
}