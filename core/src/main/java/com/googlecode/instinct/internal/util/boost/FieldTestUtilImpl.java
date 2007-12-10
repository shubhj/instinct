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

import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdgeImpl;
import com.googlecode.instinct.internal.util.lang.FieldValueSpec;
import java.lang.reflect.Field;

public final class FieldTestUtilImpl implements FieldTestUtil {
    private static final Object MARKER_STATIC_FIELD = null;
    private final FieldEdge edgeField = new FieldEdgeImpl();
    private final ClassEdge edgeClass = new ClassEdgeImpl();

    public Field get(final Class cls, final String fieldName) {
        return edgeClass.getDeclaredField(cls, fieldName);
    }

    public Field getPublic(final Class cls, final String fieldName) {
        throw new UnsupportedOperationException();
    }

    public Object getStatic(final Class cls, final String fieldName) {
        return getFieldValue(cls, MARKER_STATIC_FIELD, fieldName);
    }

    public Object getPublicStatic(final Class cls, final String fieldName) {
        throw new UnsupportedOperationException();
    }

    public Object getInstance(final Object ref, final Field field) {
        return value(ref, field);
    }

    public Object getInstance(final Object ref, final String fieldName) {
        final Class cls = ref.getClass();
        return getFieldValue(cls, ref, fieldName);
    }

    public Object getPublicInstance(final Object ref, final String fieldName) {
        throw new UnsupportedOperationException();
    }

    public void setInstance(final Object ref, final String fieldName, final Object fieldValue) {
        final Class cls = ref.getClass();
        setField(cls, ref, fieldName, fieldValue);
    }

    public void setInstance(final Object ref, final FieldValueSpec fieldValue) {
        final String name = fieldValue.getName();
        final Object value = fieldValue.getValue();
        setInstance(ref, name, value);
    }

    public void setPublicInstance(final Object ref, final String fieldName, final Object value) {
        final Class cls = ref.getClass();
        final Field field = edgeClass.getField(cls, fieldName);
        setField(ref, field, value);
    }

    public void setPublicInstance(final Object ref, final FieldValueSpec fieldValue) {
        throw new UnsupportedOperationException();
    }

    public void setStatic(final Class cls, final String fieldName, final Object fieldValue) {
        final Object ref = MARKER_STATIC_FIELD;
        setField(cls, ref, fieldName, fieldValue);
    }

    public void setStatic(final Class cls, final FieldValueSpec fieldValue) {
        final String name = fieldValue.getName();
        final Object value = fieldValue.getValue();
        setStatic(cls, name, value);
    }

    private void setField(final Class cls, final Object f, final String fieldName, final Object fieldValue) {
        final Field field = get(cls, fieldName);
        setField(f, field, fieldValue);
    }

    private void setField(final Object ref, final Field field, final Object value) {
        field.setAccessible(true);
        edgeField.set(field, ref, value);
    }

    private Object getFieldValue(final Class cls, final Object ref, final String fieldName) {
        final Field field = get(cls, fieldName);
        return value(ref, field);
    }

    private Object value(final Object ref, final Field field) {
        field.setAccessible(true);
        return edgeField.get(field, ref);
    }
}
