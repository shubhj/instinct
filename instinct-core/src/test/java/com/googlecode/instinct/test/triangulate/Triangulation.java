package com.googlecode.instinct.test.triangulate;

import java.util.List;
import java.util.Map;

public final class Triangulation {

    private Triangulation() {
    }

    private static final TestTriangulationProviderImpl TRIANAGULATION_PROVIDER
            = new TestTriangulationProviderImpl();

    public static <T> List<T> getListInstance(Class<T> elementType) {
        return TRIANAGULATION_PROVIDER.getListInstance(elementType);
    }

    public static <K, V> Map<K, V> getMapInstance(Class<K> keyType, Class<V> valueType) {
        return TRIANAGULATION_PROVIDER.getMapInstance(keyType, valueType);
    }

    public static <T> T[] getArrayInstance(Class<T> elementType) {
        return TRIANAGULATION_PROVIDER.getArrayInstance(elementType);
    }

    public static <T> T getInstance(Class<T> type) {
        return TRIANAGULATION_PROVIDER.getInstance(type);
    }

    public static Object[] getInstances(Class[] types) {
        return TRIANAGULATION_PROVIDER.getInstances(types);
    }

    public static int intInRange(int min, int max) {
        return TRIANAGULATION_PROVIDER.intInRange(min, max);
    }
}
