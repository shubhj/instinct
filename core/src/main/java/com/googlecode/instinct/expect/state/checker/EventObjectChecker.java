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

package com.googlecode.instinct.expect.state.checker;

import com.googlecode.instinct.internal.util.Suggest;
import java.util.EventObject;

@Suggest("Remove the wildcard.")
public interface EventObjectChecker<T extends EventObject> extends ObjectChecker<T> {
    void isAnEventFrom(Class<? extends EventObject> cls, Object object);

    void isAnEventFrom(Object source);

    void isNotAnEventFrom(Class<? extends EventObject> aClass, Object object);

    void isNotAnEventFrom(Object object);
}
