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

package com.googlecode.instinct.internal.reflect;

import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdgeImpl;
import com.googlecode.instinct.internal.lang.FieldValueSpec;
import com.googlecode.instinct.internal.lang.FieldValueSpecImpl;
import java.lang.reflect.Field;
import static java.lang.reflect.Modifier.isStatic;
import java.util.ArrayList;
import java.util.List;

public final class ReflectFieldMasterImpl implements ReflectFieldMaster {
    private final FieldEdge edgeField = new FieldEdgeImpl();

    public FieldValueSpec[] getInstanceFields(final Object ref) {
        final Class<?> cls = ref.getClass();
        return instanceFields(cls.getDeclaredFields(), ref);
    }

    private FieldValueSpec[] instanceFields(final Field[] fields, final Object ref) {
        final List<FieldValueSpec> list = new ArrayList<FieldValueSpec>();
        for (final Field field : fields) {
            addInstanceFields(list, field, ref);
        }
        return list.toArray(new FieldValueSpec[list.size()]);
    }

    private void addInstanceFields(final List<FieldValueSpec> list, final Field field, final Object ref) {
        if (isInstance(field)) {
            list.add(createFieldSpec(field, ref));
        }
    }

    private FieldValueSpec createFieldSpec(final Field field, final Object ref) {
        final String name = field.getName();
        final Object value = getFieldValue(field, ref);
        return new FieldValueSpecImpl(name, value);
    }

    private Object getFieldValue(final Field field, final Object ref) {
        field.setAccessible(true);
        return edgeField.get(field, ref);
    }

    private boolean isInstance(final Field field) {
        return !isStatic(field.getModifiers());
    }
}
