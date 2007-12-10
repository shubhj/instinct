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

import static java.lang.reflect.Array.newInstance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public final class TestTriangulationProviderImpl implements TestTriangulationProvider {
    private final com.googlecode.instinct.internal.util.boost.TestTriangulationProvider delegate =
            new com.googlecode.instinct.internal.util.boost.TestTriangulationProviderImpl();
    private final RandomProvider randomProvider = new RandomProviderImpl();

    public <T> List<T> getListInstance(final Class<T> elementType) {
        final List<T> result = new ArrayList<T>();
        final int numElements = intInRange(1, 5);
        for (int i = 0; i < numElements; i++) {
            result.add(getInstance(elementType));
        }
        return result;
    }

    public <K, V> Map<K, V> getMapInstance(final Class<K> keyType, final Class<V> valueType) {
        final Map<K, V> result = new HashMap<K, V>();
        final int numElements = intInRange(1, 5);
        for (int i = 0; i < numElements; i++) {
            result.put(getInstance(keyType), getInstance(valueType));
        }
        return result;
    }

    public <T> T[] getArrayInstance(final Class<T> elementType) {
        final List<T> elementList = getListInstance(elementType);
        final T[] result = (T[]) newInstance(elementType, elementList.size());
        return elementList.toArray(result);
    }

    public <T> T getInstance(final Class<T> type) {
        return (T) delegate.getInstance(type);
    }

    public Object[] getInstances(final Class[] types) {
        return delegate.getInstances(types);
    }

    public int intInRange(final int min, final int max) {
        return randomProvider.intInRange(min, max);
    }
}
