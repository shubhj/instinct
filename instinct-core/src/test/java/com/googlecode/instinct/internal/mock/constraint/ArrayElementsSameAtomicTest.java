package com.googlecode.instinct.internal.mock.constraint;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.triangulate.Triangulation;
import com.googlecode.instinct.test.checker.ClassChecker;
import org.jmock.core.Constraint;

import java.lang.reflect.Array;

public class ArrayElementsSameAtomicTest extends InstinctTestCase {
    private String[] emptyArray = new String[0];
    private String[] nonEmptyArray = Triangulation.getArrayInstance(String.class);
    private String[] anotherNonEmptyArray = Triangulation.getArrayInstance(String.class);

    public void testClassProperties() {
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
        ArrayElementsSame elementsSame = new ArrayElementsSame(expectedArray);
        assertEquals(result, elementsSame.eval(actualArray));
    }

    private <T> T[] copyArray(T[] array1) {
        T[] array2 = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length);
        System.arraycopy(array1, 0, array2, 0, array1.length);
        return array2;
    }

}
