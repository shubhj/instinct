/*
 * Copyright 2006-2007 Ben Warren
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

package com.googlecode.instinct.expect.check;

import org.hamcrest.Matchers;

// TODO Test this
public class ClassCheckerImpl<T> extends ObjectCheckerImpl<Class<T>> implements ClassChecker<T> {
    public ClassCheckerImpl(Class<T> subject) {
        super(subject);
    }

    public final <U> void typeCompatibleWith(Class<U> cls) {
        getAsserter().expectThat(subject, Matchers.typeCompatibleWith(cls));
    }

    public final <U> void notTypeCompatibleWith(Class<U> cls) {
        getAsserter().expectNotThat(subject, Matchers.typeCompatibleWith(cls));
    }
}
