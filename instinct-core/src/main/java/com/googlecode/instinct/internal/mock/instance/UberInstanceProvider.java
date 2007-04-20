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

package com.googlecode.instinct.internal.mock.instance;

import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;

public final class UberInstanceProvider implements InstanceProvider {
    // Note. Needs to be static as the concrete provider creates an instance of this class, and blows the stack.
    private static final InstanceProvider CONCRETE_INSTANCE_PROVIDER = new ConcreteInstanceProvider();
    private final InstanceProvider mockingInstanceProvider = new MockInstanceProvider();

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        if (canBeMocked(cls)) {
            return createMockInstance(cls);
        } else {
            return createConctreteInstance(cls);
        }
    }

    private Object createMockInstance(final Class<?> cls) {
        return mockingInstanceProvider.newInstance(cls);
    }

    private Object createConctreteInstance(final Class<?> cls) {
        return CONCRETE_INSTANCE_PROVIDER.newInstance(cls);
    }

    private <T> boolean canBeMocked(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return !isFinal(modifiers) || cls.isInterface();
    }
}
