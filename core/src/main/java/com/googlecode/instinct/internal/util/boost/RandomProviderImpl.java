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

package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.util.Suggest;
import java.util.Random;

@Suggest("Test double: Merge this with stub creator.")
public final class RandomProviderImpl implements RandomProvider {
    private Random random = new Random();

    // SUPPRESS CyclomaticComplexity|NPathComplexity|MethodLength {
    public Object getRandom(final Class type) {
        if (type == Boolean.class) {
            return randomBoolean();
        }
        if (type == Integer.class) {
            return randomInteger();
        }
        if (type == Long.class) {
            return randomLong();
        }
        if (type == Float.class) {
            return randomFloat();
        }
        if (type == Double.class) {
            return randomDouble();
        }
        if (type == Byte.class) {
            return randomByte();
        }
        if (type == String.class) {
            return randomString();
        }
        if (type == Class.class) {
            return randomClass();
        }
        if (type == Object.class) {
            return randomObject();
        }
        throw new UnsupportedOperationException("Hmm.  I cannot provide an instance of '" + type + "'.  " +
                "Might be worth edgifying (hiding behind an interface) this type or talking to the boosters!");
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
