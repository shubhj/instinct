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

package com.googlecode.instinct.test.triangulate;

import com.googlecode.instinct.internal.actor.TestTriangulationProvider;
import com.googlecode.instinct.internal.actor.TestTriangulationProviderImpl;
import java.util.List;
import java.util.Map;

public final class Triangulation {
    private static final TestTriangulationProvider TRIANAGULATION_PROVIDER = new TestTriangulationProviderImpl();

    private Triangulation() {
        throw new UnsupportedOperationException();
    }

    public static <T> List<T> getListInstance(final Class<T> elementType) {
        return TRIANAGULATION_PROVIDER.getListInstance(elementType);
    }

    public static <K, V> Map<K, V> getMapInstance(final Class<K> keyType, final Class<V> valueType) {
        return TRIANAGULATION_PROVIDER.getMapInstance(keyType, valueType);
    }

    public static <T> T[] getArrayInstance(final Class<T> elementType) {
        return TRIANAGULATION_PROVIDER.getArrayInstance(elementType);
    }

    public static <T> T getInstance(final Class<T> type) {
        return TRIANAGULATION_PROVIDER.getInstance(type);
    }

    public static Object[] getInstances(final Class<?>[] types) {
        return TRIANAGULATION_PROVIDER.getInstances(types);
    }

    public static int intInRange(final int min, final int max) {
        return TRIANAGULATION_PROVIDER.intInRange(min, max);
    }
}
