package com.googlecode.instinct.test.triangulate;

import au.net.netstorm.boost.test.atom.DefaultRandomProvider;

import java.util.Random;

public class RandomProviderImpl implements RandomProvider {

    private DefaultRandomProvider randomProvider = new DefaultRandomProvider();
    private Random random = new Random();

    public <T> T getRandom(Class<T> type) {
        return (T) randomProvider.getRandom(type);
    }

    public int intInRange(int min, int max) {

        if (min >= max) {
           throw new IllegalArgumentException("min of range must be less than max");
        }
        // Add one as nextInt(high) is exclusive
        int partitionSize = (max - min) + 1;
        int intInPartition = random.nextInt(partitionSize);
        return intInPartition + min;
    }
}
