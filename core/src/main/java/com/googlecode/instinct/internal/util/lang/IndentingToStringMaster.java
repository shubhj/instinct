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

package com.googlecode.instinct.internal.util.lang;

import java.lang.reflect.Array;

public final class IndentingToStringMaster implements ToStringMaster {
    private static final String COMMA = ",";
    private static final String LF = System.getProperty("line.separator");
    private static final String INDENT = "    ";
    private final ReflectMaster reflect = new ReflectMasterImpl();

    public String getString(final Object ref) {
        return ref.getClass().getSimpleName() + formatFields(formatFields(ref));
    }

    private String formatFields(final String[] s) {
        if (s.length == 0) {
            return "[]";
        }
        return "[" + LF + indent(getString(s)) + LF + "]";
    }

    private String getString(final String[] s) {
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += s[i];
            if (i < s.length - 1) {
                result += LF;
            }
        }
        return result;
    }

    private String[] formatFields(final Object ref) {
        final FieldValueSpec[] fields = reflect.getInstanceFields(ref);
        final String[] result = new String[fields.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = formatField(fields[i]);
        }
        return result;
    }

    private String formatField(final FieldValueSpec fieldValue) {
        return fieldValue.getName() + "=" + fieldValue(fieldValue);
    }

    private String fieldValue(final FieldValueSpec fieldValue) {
        final Object value = fieldValue.getValue();
        // FIX SC600 incorporate and test this.
        if (value == null) {
            return "null";
        }
        return isArray(value) ? arrayValue(value) : value.toString();
    }

    private String arrayValue(final Object array) {
        return "{" + arrayToString(array) + "}";
    }

    private String arrayToString(final Object array) {
        String result = "";
        for (int i = 0; i < Array.getLength(array); i++) {
            result += Array.get(array, i).toString() + COMMA;
        }
        return removeLastChar(result);
    }

    private boolean isArray(final Object value) {
        return value.getClass()
                .isArray();
    }

    private String removeLastChar(final String s) {
        return s.length() == 0 ? s : s.substring(0, s.length() - 1);
    }

    private String indent(final String s) {
        return INDENT + s.replaceAll(LF, LF + INDENT);
    }
}
