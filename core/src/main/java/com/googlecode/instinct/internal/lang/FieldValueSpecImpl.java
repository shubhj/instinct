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

package com.googlecode.instinct.internal.lang;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.reflect.Array;

public final class FieldValueSpecImpl implements FieldValueSpec {
    private final String name;
    private final Object value;

    // Note. We cannot reject null values here as fields may be null.
    public FieldValueSpecImpl(final String name, final Object value) {
        checkNotNull(name);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj.getClass().equals(FieldValueSpecImpl.class) && checkDefaultFieldSpec((FieldValueSpec) obj);
    }

    @Override
    public int hashCode() {
        return 31 * (name == null ? 0 : name.hashCode()) + (value == null ? 0 : value.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ",value=" + value + "]";
    }

    private boolean checkDefaultFieldSpec(final FieldValueSpec spec) {
        return spec.getName().equals(name) && checkValue(spec);
    }

    private boolean checkValue(final FieldValueSpec spec) {
        if (spec.getValue() == null) {
            return spec.getValue() == value;
        }
        if (spec.getValue().getClass().isArray()) {
            return arraysEquals(spec);
        }
        return spec.getValue().equals(value);
    }

    private boolean arraysEquals(final FieldValueSpec spec) {
        if (Array.getLength(spec.getValue()) != Array.getLength(value)) {
            return false;
        }
        for (int i = 0; i < Array.getLength(spec.getValue()); i++) {
            if (memberArraysUnequal(spec, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean memberArraysUnequal(final FieldValueSpec spec, final int i) {
        final Object o1 = Array.get(spec.getValue(), i);
        final Object o2 = Array.get(value, i);
        return !o1.equals(o2);
    }
}
