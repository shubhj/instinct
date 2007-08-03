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

package com.googlecode.instinct.internal.mock.constraint;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ClassChecker;
import static com.googlecode.instinct.test.triangulate.Triangulation.getArrayInstance;
import java.lang.reflect.Array;
import org.jmock.core.Constraint;

@SuppressWarnings({"unchecked"})
public class ArrayElementsSameAtomicTest extends InstinctTestCase {
    private String[] emptyArray = new String[0];
    private String[] nonEmptyArray = getArrayInstance(String.class);
    private String[] anotherNonEmptyArray = getArrayInstance(String.class);

    public void testConformsToClassTraits() {
        ClassChecker.checkClass(ArrayElementsSame.class, Constraint.class);
    }

    public void testBothEmptyReturnsTrue() {
        checkEvalBehaviour(true, emptyArray, copyArray(emptyArray));
    }

    public void testBothWithSameElementsReturnsTrue() {
        checkEvalBehaviour(true, nonEmptyArray, copyArray(nonEmptyArray));
    }

    public void testBothWithDifferentElementsReturnsFalse() {
        checkEvalBehaviour(false, nonEmptyArray, anotherNonEmptyArray);
    }

    public void testEmptyExpectedAndNonEmptyActualReturnsFalse() {
        checkEvalBehaviour(false, emptyArray, nonEmptyArray);
    }

    public void testNonEmptyExpectedAndEmptyActualReturnsFalse() {
        checkEvalBehaviour(false, nonEmptyArray, emptyArray);
    }

    private void checkEvalBehaviour(boolean result, String[] expectedArray, String[] actualArray) {
        Constraint elementsSame = new ArrayElementsSame(expectedArray);
        assertEquals(result, elementsSame.eval(actualArray));
    }

    private <T> T[] copyArray(T[] array1) {
        T[] array2 = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length);
        System.arraycopy(array1, 0, array2, 0, array1.length);
        return array2;
    }
}
