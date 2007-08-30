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

package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.internal.mock.instance.UberInstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;

public final class DummyCreator implements ActorCreator {
    @Suggest("This isn't really creating dummies. Need to wrap a proxy around (non-final) classes and error out if methods called.")
    @SuppressWarnings({"unchecked"})
    public <T> T create(final Class<T> type, final String roleName) {
        return (T) new UberInstanceProvider().newInstance(type);
    }
}
