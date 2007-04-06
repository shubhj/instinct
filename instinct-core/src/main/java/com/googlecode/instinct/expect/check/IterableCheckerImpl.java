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

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

// TODO Test this
public class IterableCheckerImpl<E, T extends Iterable<E>>
        extends ObjectCheckerImpl<T> implements IterableChecker<E, T> {

    public IterableCheckerImpl(T subject) {
        super(subject);
    }

    public final void containsItem(E element) {
        getAsserter().expectThat(subject, Matchers.hasItem(element));
    }

    public final void containsItem(Matcher<E> matcher) {
        getAsserter().expectThat(subject, Matchers.hasItem(matcher));
    }

    public final void notContainItem(E item) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(item));
    }

    public final void notContainItem(Matcher<E> matcher) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(matcher));
    }

    public final void containsItems(Matcher<E>... matchers) {
        getAsserter().expectThat(subject, Matchers.hasItems(matchers));
    }

    public final void containsItems(E... items) {
        getAsserter().expectThat(subject, Matchers.hasItems(items));
    }

    public final void notContainItems(Matcher<E>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(matchers));
    }

    public final void notContainItems(E... items) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(items));
    }
}
