/*
 * Copyright 2006-2007 Workingmouse
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

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.ConcreteInstanceProvider;

@Suggest({"Fill stub arrays with stubs ala mock creator.", "We should be able to stub out interfaces as well, ",
        "by wrapping in a proxy that returns a stub value (the type is the method return type) from an instance provider call"})
public final class StubCreator implements SpecificationDoubleCreator {
    private final InstanceProvider instanceProvider = new ConcreteInstanceProvider();

    @SuppressWarnings({"unchecked", "CatchGenericClass"})
    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        checkNotNull(doubleType, roleName);
        try {
            return (T) instanceProvider.newInstance(doubleType);
        } catch (Throwable e) {
            final String message = "Unable to create stub "
                    + doubleType.getName() + " (with role name '" + roleName + "'). Stub types must be non-abstract classes.";
            throw new SpecificationDoubleCreationException(message, e);
        }
    }
}

