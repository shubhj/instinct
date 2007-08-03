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

package com.googlecode.instinct.expect.state;

import java.util.EventObject;
import org.hamcrest.Matchers;

// TODO Test this
public class EventObjectCheckerImpl<T extends EventObject>
        extends ObjectCheckerImpl<T> implements EventObjectChecker<T> {

    public EventObjectCheckerImpl(T subject) {
        super(subject);
    }

    public final void eventFrom(Class<? extends EventObject> cls, Object object) {
        getAsserter().expectThat(subject, Matchers.eventFrom(cls, object));
    }

    public final void eventFrom(Object object) {
        getAsserter().expectThat(subject, Matchers.eventFrom(object));
    }

    public final void notEventFrom(Class<? extends EventObject> aClass, Object object) {
        getAsserter().expectNotThat(subject, Matchers.eventFrom(aClass, object));
    }

    public final void notEventFrom(Object object) {
        getAsserter().expectNotThat(subject, Matchers.eventFrom(object));
    }
}
