package com.googlecode.instinct.test.triangulate;


import java.util.*;
import java.lang.reflect.Array;

public class TestTriangulationProviderImpl implements TestTriangulationProvider {

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

    public <K,V> Map<K, V> getMapInstance(Class<K> keyType, Class<V> valueType) {
        Map<K, V> result = new HashMap<K,V>();
        int numElements = intInRange(1, 5);
        for (int i = 0; i < numElements; i++) {
            result.put(getInstance(keyType), getInstance(valueType));
        }
        return result;
    }

    public <T> T[] getArrayInstance(Class<T> elementType) {
        List<T> elementList =  getListInstance(elementType);
        T[] result = (T[]) Array.newInstance(elementType, elementList.size());
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
