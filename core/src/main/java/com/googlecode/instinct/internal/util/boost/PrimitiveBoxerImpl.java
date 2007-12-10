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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO Remove this, use the boxer in the main codebase.
final class PrimitiveBoxerImpl implements PrimitiveBoxer {
    private final Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

    {
        map.put(boolean.class, Boolean.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
        map.put(byte.class, Byte.class);
        map.put(char.class, Character.class);
    }

    @SuppressWarnings({"unchecked"})
    public <T, U> Class<U> getBoxed(final Class<T> primitive) {
        if (!isPrimitive(primitive)) {
            throw new RuntimeException(primitive + " is not a primitive type");
        }
        return (Class<U>) map.get(primitive);
    }

    public <T> boolean isPrimitive(final Class<T> candidate) {
        return map.get(candidate) != null;
    }

    public <T> boolean isBoxed(final Class<T> candidate) {
        final Collection values = map.values();
        return values.contains(candidate);
    }

}
