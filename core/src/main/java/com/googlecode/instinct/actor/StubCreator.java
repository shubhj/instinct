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

package com.googlecode.instinct.actor;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.ConcreteInstanceProvider;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

@Suggest("Should be able to pass in a class to the mock that checks for valid values - Discriminator?")
public final class StubCreator implements SpecificationDoubleCreator {
    private final InstanceProvider instanceProvider = new ConcreteInstanceProvider();

    //SUPPRESS IllegalCatch {
    @SuppressWarnings({"unchecked", "CatchGenericClass"})
    public <T> T createDouble(final Class<T> doubleType, final String roleName) {
        checkNotNull(doubleType, roleName);
        try {
            return instanceProvider.newInstance(doubleType);
        } catch (Throwable e) {
            // TODO This needs to change, should be able to stub this stuff out.
            final String message =
                    "Unable to create stub " + doubleType.getName() + " (with role name '" + roleName + "'). Stub types cannot be abstract classes.";
            throw new SpecificationDoubleCreationException(message, e);
        }
    }
    // } SUPPRESS IllegalCatch
}

