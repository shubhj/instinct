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
import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeField;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeField;
import com.googlecode.instinct.core.TestDoubleConfigurationException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class TestDoubleFieldCheckerImpl implements TestDoubleFieldChecker {
    @Suggest({"Add getModifiers() to edge", "Create EdgeModifer"})
    private final EdgeField edgeField = new DefaultEdgeField();

    public void checkField(final Field field, final Object instance) {
        checkNotNull(field, instance);
        rejectFinalModifiers(field);
        rejectNonNullValue(field, instance);
    }

    private void rejectFinalModifiers(final Field field) {
        if (isFinal(field.getModifiers())) {
            throw new TestDoubleConfigurationException("Field " + field.getName() + " cannot be final");
        }
    }

    private void rejectNonNullValue(final Field field, final Object instance) {
        field.setAccessible(true);
        if (edgeField.get(field, instance) != null) {
            throw new TestDoubleConfigurationException("Field " + field.getName() + " must be null");
        }
    }
}
