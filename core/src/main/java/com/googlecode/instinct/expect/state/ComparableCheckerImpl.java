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

package com.googlecode.instinct.expect.state;

import org.hamcrest.Matchers;

// TODO Test this
public class ComparableCheckerImpl<T extends Comparable<T>> extends ObjectCheckerImpl<T>
        implements ComparableChecker<T> {

    public ComparableCheckerImpl(T subject) {
        super(subject);
    }

    public final void greaterThan(T comparable) {
        getAsserter().expectThat(subject, Matchers.greaterThan(comparable));
    }

    public final void greaterThanOrEqualTo(T comparable) {
        getAsserter().expectThat(subject, Matchers.greaterThanOrEqualTo(comparable));
    }

    public final void lessThan(T comparable) {
        getAsserter().expectThat(subject, Matchers.lessThan(comparable));
    }

    public final void lessThanOrEqualTo(T comparable) {
        getAsserter().expectThat(subject, Matchers.lessThanOrEqualTo(comparable));
    }
}
