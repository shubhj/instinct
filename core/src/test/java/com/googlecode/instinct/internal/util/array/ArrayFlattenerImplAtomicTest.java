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

package com.googlecode.instinct.internal.util.array;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ArrayFlattenerImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = ArrayFlattenerImpl.class) private ArrayFlattener flattener;

    public void testConformsToClassTraits() {
        checkClass(ArrayFlattenerImpl.class, ArrayFlattener.class);
    }

    public void testFlattenNoOp() {
        final Object[] expectedFlattening1 = new Object[]{"1", "2"};
        final Object[] unflattened1 = new Object[]{"1", "2"};
        expect.that(flattener.flatten(unflattened1)).isEqualTo(expectedFlattening1);
        final Object[] expectedFlattening = new String[]{"1", "2"};
        final Object[] unflattened = new String[]{"1", "2"};
        expect.that(flattener.flatten(unflattened)).isEqualTo(expectedFlattening);
    }

    public void testNestedOneLevelFlatten() {
        final Object[] unflattened = {"1", "2", new Object[]{"3", "4"}};
        final Object[] expected = {"1", "2", "3", "4"};
        expect.that(flattener.flatten(unflattened)).isEqualTo(expected);
    }

    public void testNestedTwoLevelFlatten() {
        final Object[] unflattened = {"1", "2", new Object[]{"3", "4", new Object[]{"5", "6"}}};
        final Object[] expected = {"1", "2", "3", "4", "5", "6"};
        expect.that(flattener.flatten(unflattened)).isEqualTo(expected);
    }

    public void testNestedThreeLevelFlatten() {
        final Object[] unflattened = {"1", "2", new Object[]{"3", "4", new Object[]{"5", "6", new Object[]{"7", "8"}}}};
        final Object[] expected = {"1", "2", "3", "4", "5", "6", "7", "8"};
        expect.that(flattener.flatten(unflattened)).isEqualTo(expected);
    }

    public void testMixedTypeFlatten() {
        final Object[] unflattened = {getClass(), this, new Number[]{(long) 1, 3}, new Class[]{Object.class}};
        final Object[] expected = {getClass(), this, (long) 1, 3, Object.class};
        expect.that(flattener.flatten(unflattened)).isEqualTo(expected);
    }
}
