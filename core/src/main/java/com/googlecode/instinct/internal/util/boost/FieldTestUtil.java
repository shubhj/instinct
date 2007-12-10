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

import com.googlecode.instinct.internal.util.lang.FieldValueSpec;
import java.lang.reflect.Field;

public interface FieldTestUtil {
    Field get(Class cls, String fieldName);

    Field getPublic(Class cls, String fieldName);

    Object getStatic(Class cls, String fieldName);

    Object getPublicStatic(Class cls, String fieldName);

    Object getInstance(Object ref, Field field);

    Object getInstance(Object ref, String fieldName);

    Object getPublicInstance(Object ref, String fieldName);

    void setInstance(Object ref, String fieldName, Object value);

    void setInstance(Object ref, FieldValueSpec fieldValue);

    void setPublicInstance(Object ref, String fieldName, Object value);

    void setPublicInstance(Object ref, FieldValueSpec fieldValue);

    void setStatic(Class cls, String fieldName, Object value);

    void setStatic(Class cls, FieldValueSpec fieldValue);
}
