package com.googlecode.instinct.test.triangulate;

import java.util.Random;
import au.net.netstorm.boost.test.atom.DefaultRandomProvider;
import com.googlecode.instinct.internal.util.Suggest;

@SuppressWarnings({"unchecked"})
@Suggest("How does this differ from TriangulationProvider? Can they be merged?")
public final class RandomProviderImpl implements RandomProvider {
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
