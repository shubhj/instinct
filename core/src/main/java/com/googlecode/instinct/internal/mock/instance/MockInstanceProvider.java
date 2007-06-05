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

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import static com.googlecode.instinct.expect.Mocker12.mock;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("May be better to use dynamic proxies rather than mocks?")
public final class MockInstanceProvider implements InstanceProvider {
    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return mock(cls);
    }
}
