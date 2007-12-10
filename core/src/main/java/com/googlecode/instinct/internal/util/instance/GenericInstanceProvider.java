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

package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.internal.actor.DummyCreator;
import com.googlecode.instinct.internal.actor.SpecificationDoubleCreator;
import com.googlecode.instinct.internal.actor.StubCreator;
import com.googlecode.instinct.internal.util.Suggest;
import java.io.File;
import static java.lang.reflect.Modifier.isFinal;
import static java.util.Arrays.asList;
import java.util.List;

@Suggest("Rename this class... Or remove it... Used for null checking fields. NullCheckCandidateValuesInstanceProvider?")
public final class GenericInstanceProvider implements InstanceProvider {
    private static final SpecificationDoubleCreator DUMMY_CREATOR = new DummyCreator();
    private static final SpecificationDoubleCreator STUB_CREATOR = new StubCreator();

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public <T> T newInstance(final Class<T> cls) {
        if (cannotBeDummied(cls)) {
            return STUB_CREATOR.createDouble(cls, cls.getSimpleName());
        } else {
            return DUMMY_CREATOR.createDouble(cls, cls.getSimpleName());
        }
    }

    private <T> boolean cannotBeDummied(final Class<T> cls) {
        final List<Class<?>> excludedFromDummying = excludedFromDummying();
        return cls.isEnum() || cls.isPrimitive() || isFinal(cls.getModifiers()) || excludedFromDummying.contains(cls);
    }

    // Note. Some classes have methods called when their toString() is called (e.g. for error reporting), and hence cannot be dummied.
    private List<Class<?>> excludedFromDummying() {
        final Class<?>[] excluded = {File.class};
        return asList(excluded);
    }
}
