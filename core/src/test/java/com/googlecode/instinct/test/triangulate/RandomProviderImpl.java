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

import au.net.netstorm.boost.test.atom.DefaultRandomProvider;
import com.googlecode.instinct.internal.util.Suggest;
import java.util.Random;

@SuppressWarnings({"unchecked"})
@Suggest("How does this differ from TriangulationProvider? Can they be merged?")
public final class RandomProviderImpl implements RandomProvider {
    private DefaultRandomProvider randomProvider = new DefaultRandomProvider();
    private Random random = new Random();

    public <T> T getRandom(final Class<T> type) {
        return (T) randomProvider.getRandom(type);
    }

    public int intInRange(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min of range must be less than max");
        }
        // Add one as nextInt(high) is exclusive
        final int partitionSize = (max - min) + 1;
        final int intInPartition = random.nextInt(partitionSize);
        return intInPartition + min;
    }
}
