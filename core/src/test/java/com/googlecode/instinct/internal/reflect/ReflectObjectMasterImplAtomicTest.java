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

package com.googlecode.instinct.internal.reflect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import java.lang.reflect.Constructor;
import java.util.Map;

public final class ReflectObjectMasterImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = ReflectObjectMasterImpl.class) private ReflectObjectMaster master;

    public void testFailsWithMultipleConstructors() {
        checkFailsWithMultipleConstructors(TestTwoConstructors.class);
        checkFailsWithMultipleConstructors(TestThreeConstructors.class);
    }

    public void testSingleConstructor() {
        checkSingleConstructor(TestOneConstructor.class);
        checkSingleConstructor(TestOnePrivateConstructor.class);
    }

    public void testFailsWithInterfaces() {
        expectException(IllegalArgumentException.class, new Runnable() {
            public void run() {
                master.getConstructor(TestInterfaceOne.class);
            }
        });
    }

    private <T> void checkSingleConstructor(final Class<T> cls) {
        final Constructor<?>[] constructors = cls.getDeclaredConstructors();
        expect.that(constructors).isOfSize(1);
        expect.that(constructors).containsItem(master.getConstructor(cls));
    }

    private <T> void checkFailsWithMultipleConstructors(final Class<T> cls) {
        expectException(IllegalStateException.class, new Runnable() {
            public void run() {
                master.getConstructor(cls);
            }
        });
    }

    @SuppressWarnings({"ALL"})
    private interface TestInterfaceOne {
        void fridayIsHere();

        Integer getSmeetOthEchuRchontIme(String frankyfurter, Map beanTypes);
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOneConstructor {
        public TestOneConstructor() {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestOnePrivateConstructor {
        private TestOnePrivateConstructor() {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestTwoConstructors {
        private TestTwoConstructors() {
        }

        private TestTwoConstructors(String aString) {
        }
    }

    @SuppressWarnings({"ALL"})
    private static final class TestThreeConstructors {
        private TestThreeConstructors() {
        }

        private TestThreeConstructors(String aString) {
        }

        private TestThreeConstructors(int anInt) {
        }
    }
}
