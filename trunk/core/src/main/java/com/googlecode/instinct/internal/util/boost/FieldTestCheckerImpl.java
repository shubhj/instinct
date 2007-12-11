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

package com.googlecode.instinct.internal.util.boost;

import java.lang.reflect.Field;
import junit.framework.Assert;

public final class FieldTestCheckerImpl implements FieldTestChecker {
    private final FieldTestUtil fieldTestUtil = new FieldTestUtilImpl();
    private final ModifierTestChecker modifiers = new ModifierTestCheckerImpl();

    public void checkPrivateFinalInstanceField(final Class type, final String fieldName) {
        final Field declared = fieldTestUtil.get(type, fieldName);
        modifiers.checkPrivateFinalInstance(declared);
    }

    public void checkInstanceType(final Class expectedType, final Object ref, final String fieldName) {
        final Object value = fieldTestUtil.getInstance(ref, fieldName);
        final Class type = value.getClass();
        Assert.assertEquals(expectedType, type);
    }
}