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

package com.googlecode.instinct.internal.actor;

import com.googlecode.instinct.internal.util.Suggest;
import java.util.Random;

@Suggest("Test double: Merge this with stub creator.")
public final class RandomProviderImpl implements RandomProvider {
    private Random random = new Random();

    // SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength {
    @SuppressWarnings({"unchecked"})
    public <T> T randomValue(final Class<T> type) {
        if (type == Boolean.class) {
            return (T) randomBoolean();
        }
        if (type == Integer.class) {
            return (T) randomInteger();
        }
        if (type == Long.class) {
            return (T) randomLong();
        }
        if (type == Float.class) {
            return (T) randomFloat();
        }
        if (type == Double.class) {
            return (T) randomDouble();
        }
        if (type == Byte.class) {
            return (T) randomByte();
        }
        if (type == String.class) {
            return (T) randomString();
        }
        if (type == Class.class) {
            return (T) randomClass();
        }
        if (type == Object.class) {
            return (T) randomObject();
        }
        throw new UnsupportedOperationException("Hmm.  I cannot provide an instance of '" + type + "'.  " +
                "Might be worth edgifying (hiding behind an interface) this type or talking to the boosters!");
    }

    public int randomIntInRange(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min of range must be less than max");
        }
        // Add one as nextInt(high) is exclusive
        final int partitionSize = (max - min) + 1;
        final int intInPartition = random.nextInt(partitionSize);
        return intInPartition + min;
    }

    // } SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength

    private Object randomClass() {
        return InternalInterface.class;
    }

    private Object randomString() {
        return "Some random string " + random.nextLong();
    }

    private Object randomBoolean() {
        return random.nextBoolean();
    }

    private Object randomInteger() {
        return random.nextInt();
    }

    private Object randomLong() {
        return random.nextLong();
    }

    private Object randomFloat() {
        return random.nextFloat();
    }

    private Object randomDouble() {
        return random.nextDouble();
    }

    private Object randomByte() {
        final byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0];
    }

    private Object randomObject() {
        return new Object();
    }

    private interface InternalInterface {
    }
}
