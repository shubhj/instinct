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

package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getField;

public final class DummyTestDoubleCreatorAtomicTest extends InstinctTestCase {
    private TestDoubleCreator creator;

    public void testProperties() {
        checkClass(DummyTestDoubleCreator.class, TestDoubleCreator.class);
    }

    @Suggest("Lyall & Tom were here!")
    public void testCreateValue() {
        final Field field = getField(AClassIsAWonderfulThing.class, "aFieldOfGrass");
        final Object dummyValue = creator.createValue(field);
        assertNotNull(dummyValue);
    }

    @Override
    public void setUpSubject() {
        creator = new DummyTestDoubleCreator();
    }

    private static final class AClassIsAWonderfulThing {
        private String aFieldOfGrass;
    }
}
