/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.edge.java.lang.reflect;

import com.googlecode.instinct.internal.edge.EdgeException;
import java.lang.reflect.Field;

public final class FieldEdgeImpl implements FieldEdge {
    public Object get(final Field field, final Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new EdgeException(e);
        }
    }

    public void set(final Field field, final Object obj, final Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new EdgeException(e);
        }
    }
}
