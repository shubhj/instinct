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

package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import java.util.Arrays;

public final class FieldBasedEqualsMasterAtomicTest extends InstinctTestCase {
    private final EqualsMaster master = new FieldBasedEqualsMaster();
    private static final Object INTEGER = 1;
    private static final Object STRING = "test";

    public void testNullParams() {
        checkEquals(true, null, null);
        checkEquals(false, null, STRING);
        checkEquals(false, STRING, null);
    }

    public void testStringEquals() {
        checkEquals(true, "HI", "HI");
        checkEquals(false, "HI", "THERE");
    }

    public void testDifferentObjects() {
        checkEquals(false, STRING, INTEGER);
    }

    public void testObjectsEqual() {
        final Object o1 = new TestObject("test", 1);
        final Object o2 = new TestObject("test", 1);
        checkEquals(true, o1, o2);
    }

    public void testObjectsNotEqual() {
        final Object o1 = new TestObject("test", 1);
        final Object o2 = new TestObject("test", 2);
        checkEquals(false, o1, o2);
    }

    public void testNotEqualsTriangulate() {
        final Object o1 = new TestObject("hello", 5);
        final Object o2 = new TestObject("there", 5);
        checkEquals(false, o1, o2);
    }

    public void testDifferentClasses() {
        final Object o1 = new TestObject("test", 1);
        final Object o2 = new AnotherTestObject("test", 1);
        checkEquals(false, o1, o2);
    }

    public void testSubClassesDifferent() {
        final Object o1 = new TestObject("test", 1);
        final Object o2 = new SubClassTestObject("test", 1);
        checkEquals(false, o1, o2);
    }

    public void testByteArraysEqual() {
        checkByteArrays(true, "luke", "luke");
        checkByteArrays(false, "luke", "lukeskywalker");
        checkByteArrays(false, "luke", "lu");
    }

    public void testIntArraysEqual() {
        final int[] intArray = {1};
        final Object o1 = new IntArrayTestObject(intArray);
        final Object o2 = new IntArrayTestObject(intArray);
        checkEquals(true, o1, o2);
    }

    public void testObjectArrayEquals() {
        final Object o1 = new ObjectArrayTestObject(new Object[]{"x", "y"});
        final Object o2 = new ObjectArrayTestObject(new Object[]{"x", "y"});
        checkEquals(true, o1, o2);
    }

    private void checkByteArrays(final boolean expected, final String firstString, final String secondString) {
        final Object o1 = new ByteArrayTestObject(firstString.getBytes());
        final Object o2 = new ByteArrayTestObject(secondString.getBytes());
        checkEquals(expected, o1, o2);
    }

    private void checkEquals(final boolean expected, final Object o1, final Object o2) {
        expect.that(master.equals(o1, o2)).isEqualTo(expected);
        expect.that(master.equals(o2, o1)).isEqualTo(expected);
    }

    private static class TestObject {
        private String aString;
        private int anInt;

        public TestObject(final String aString, final int anInt) {
            this.aString = aString;
            this.anInt = anInt;
        }

        @Override
        public String toString() {
            return "For Checkstyle TestObject[aString=" + aString + ",anInt=" + anInt + "]";
        }
    }

    private static final class SubClassTestObject extends TestObject {
        public SubClassTestObject(final String aString, final int anInt) {
            super(aString, anInt);
        }
    }

    private static final class ByteArrayTestObject {
        private byte[] buffer;

        public ByteArrayTestObject(final byte[] buffer) {
            this.buffer = buffer;
        }

        @Override
        public String toString() {
            return "For Checkstyle ArrayTestObject[aBuffer=" + new String(buffer) + "]";
        }
    }

    private static class ObjectArrayTestObject {
        private Object[] buffer;

        public ObjectArrayTestObject(final Object[] buffer) {
            this.buffer = buffer;
        }

        @Override
        public String toString() {
            return "For Checkstyle ArrayTestObject[aBuffer=" + Arrays.asList(buffer) + "]";
        }
    }

    private static final class AnotherTestObject {
        private String aString;
        private int anInt;

        public AnotherTestObject(final String aString, final int anInt) {
            this.aString = aString;
            this.anInt = anInt;
        }

        @Override
        public String toString() {
            return "For Checkstyle AnotherTestObject[aString=" + aString + ",anInt=" + anInt + "]";
        }
    }

    private static final class IntArrayTestObject {
        private int[] buffer;

        public IntArrayTestObject(final int[] buffer) {
            this.buffer = buffer;
        }

        @Override
        public String toString() {
            return "For Checkstyle ArrayTestObject[aBuffer=" + Arrays.toString(buffer) + "]";
        }
    }
}
