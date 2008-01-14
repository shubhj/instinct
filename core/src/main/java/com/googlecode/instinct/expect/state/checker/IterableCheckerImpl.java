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

import com.googlecode.instinct.internal.util.Fix;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.describedAs;

@Fix("Test this")
public class IterableCheckerImpl<E, T extends Iterable<E>> extends ObjectCheckerImpl<T> implements IterableChecker<E, T> {
    public IterableCheckerImpl(final T subject) {
        super(subject);
    }

    public void isEmpty() {
        getAsserter().expectThat(subject.iterator().hasNext(), describedAs("iterator().hasNext() = <false>", Matchers.equalTo(false)));
    }

    public void isNotEmpty() {
        getAsserter().expectThat(subject.iterator().hasNext(), describedAs("iterator().hasNext() = <true>", Matchers.equalTo(true)));
    }

    public final void containsItem(final E item) {
        getAsserter().expectThat(subject, Matchers.hasItem(item));
    }

    public final void containsItem(final Matcher<E> matcher) {
        getAsserter().expectThat(subject, Matchers.hasItem(matcher));
    }

    public final void doesNotContainItem(final E item) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(item));
    }

    public final void doesNotContainItem(final Matcher<E> matcher) {
        getAsserter().expectNotThat(subject, Matchers.hasItem(matcher));
    }

    public final void containsItems(final Matcher<E>... matchers) {
        getAsserter().expectThat(subject, Matchers.hasItems(matchers));
    }

    public final void containsItems(final E... items) {
        getAsserter().expectThat(subject, Matchers.hasItems(items));
    }

    @SuppressWarnings({"unchecked"})
    public final void containsItems(final Collection<E> items) {
        getAsserter().expectThat(subject, Matchers.hasItems((E[]) items.toArray()));
    }

    public final void doesNotContainItems(final Matcher<E>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(matchers));
    }

    public final void doesNotContainItems(final E... items) {
        getAsserter().expectNotThat(subject, Matchers.hasItems(items));
    }
}
