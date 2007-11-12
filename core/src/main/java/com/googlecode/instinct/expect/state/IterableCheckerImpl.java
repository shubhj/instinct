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

import com.googlecode.instinct.internal.util.Fix;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

@Fix("Test this")
public class IterableCheckerImpl<E, T extends Iterable<E>> extends ObjectCheckerImpl<T> implements IterableChecker<E, T> {

    public IterableCheckerImpl(final T subject) {
        super(subject);
    }

    public final void containsItem(final E element) {
        getAsserter().expectThat(subject, Matchers.hasItem(element));
    }

    public final void containsItem(final Matcher<E> matcher) {
        getAsserter().expectThat(subject, Matchers.hasItem(matcher));
    }

    public final void notContainItem(final E item) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(item));
    }

    public final void notContainItem(final Matcher<E> matcher) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(matcher));
    }

    public final void containsItems(final Matcher<E>... matchers) {
        getAsserter().expectThat(subject, Matchers.hasItems(matchers));
    }

    public final void containsItems(final E... items) {
        final Matcher<Iterable<E>> iterableMatcher = Matchers.hasItems(items);
        getAsserter().expectThat(subject, iterableMatcher);
    }

    @SuppressWarnings({"unchecked"})
    public final void containsItems(final Collection<E> items) {
        getAsserter().expectThat(subject, Matchers.hasItems((E[]) items.toArray()));
    }

    public final void notContainItems(final Matcher<E>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(matchers));
    }

    public final void notContainItems(final E... items) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(items));
    }
}
