package com.googlecode.instinct.test.triangulate;

import java.util.List;
import java.util.Map;

public interface TestTriangulationProvider {
    <T> T getInstance(Class<T> type);

    Object[] getInstances(Class[] types);

    <T> List<T> getListInstance(Class<T> elementType);

    <K,V> Map<K, V> getMapInstance(Class<K> keyType, Class<V> valueType);

    <T> T[] getArrayInstance(Class<T> elementType);

    int intInRange(int min, int max);
}
