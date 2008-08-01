/*
 * Copyright 2008 Tom Adams
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

import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.expect.state.matcher.ExistentialListMatcher;
import com.googlecode.instinct.expect.state.matcher.UniversalListMatcher;
import fj.F;
import fj.data.List;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class ListCheckerImpl<T> extends ObjectCheckerImpl<List<T>> implements ListChecker<T> {
    public ListCheckerImpl(final List<T> subject) {
        super(subject);
    }

    public void isEmpty() {
        getAsserter().expectThat(subject.toArray().length() == 0, Matchers.describedAs("length == 0", equalTo(true)));
    }

    public void isNotEmpty() {
        getAsserter().expectThat(subject.toArray().length() != 0, Matchers.describedAs("length != 0", equalTo(true)));
    }

    public void isOfSize(final int size) {
        getAsserter().expectThat(subject.toArray().length(), equalTo(size));
    }

    public void containsItem(final Matcher<T> matcher) {
        getAsserter().expectThat(subject, ExistentialListMatcher.hasItemInList(matcher));
    }

    public void contains(final F<T, Boolean> matching) {
        getAsserter().expectThat(subject, ExistentialListMatcher.hasItemInList(matching));
    }

    public void containsItem(final T item) {
        getAsserter().expectThat(subject, ExistentialListMatcher.hasItemInList(item));
    }

    public void allItemsMatch(final Matcher<T> matcher) {
        getAsserter().expectThat(subject, UniversalListMatcher.allItemsMatch(matcher));
    }

    public void allItemsMatch(final F<T, Boolean> matching) {
        getAsserter().expectThat(subject, UniversalListMatcher.allItemsMatch(matching));
    }

    public void allItemsMatch(final T item) {
        getAsserter().expectThat(subject, UniversalListMatcher.allItemsMatch(item));
    }

    public void doesNotContainItem(final Matcher<T> matcher) {
        getAsserter().expectNotThat(subject, ExistentialListMatcher.hasItemInList(matcher));
    }

    public void doesNotContain(final F<T, Boolean> matching) {
        getAsserter().expectNotThat(subject, ExistentialListMatcher.hasItemInList(matching));
    }

    public void doesNotContainItem(final T item) {
        getAsserter().expectNotThat(subject, ExistentialListMatcher.hasItemInList(item));
    }
}
