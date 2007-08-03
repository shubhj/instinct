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
    private final au.net.netstorm.boost.test.atom.TestTriangulationProvider delegate =
            new au.net.netstorm.boost.test.atom.TestTriangulationProvider();
    private final RandomProvider randomProvider = new RandomProviderImpl();

    public <T> List<T> getListInstance(Class<T> elementType) {
        List<T> result = new ArrayList<T>();
        int numElements = intInRange(1, 5);
        for (int i = 0; i < numElements; i++) {
            result.add(getInstance(elementType));
        }
        return result;
    }

    public <K, V> Map<K, V> getMapInstance(Class<K> keyType, Class<V> valueType) {
        Map<K, V> result = new HashMap<K, V>();
        int numElements = intInRange(1, 5);
        for (int i = 0; i < numElements; i++) {
            result.put(getInstance(keyType), getInstance(valueType));
        }
        return result;
    }

    public <T> T[] getArrayInstance(Class<T> elementType) {
        List<T> elementList = getListInstance(elementType);
        T[] result = (T[]) newInstance(elementType, elementList.size());
        return elementList.toArray(result);
    }

    public <T> T getInstance(Class<T> type) {
        return (T) delegate.getInstance(type);
    }

    public Object[] getInstances(Class[] types) {
        return delegate.getInstances(types);
    }

    public int intInRange(int min, int max) {
        return randomProvider.intInRange(min, max);
    }
}
