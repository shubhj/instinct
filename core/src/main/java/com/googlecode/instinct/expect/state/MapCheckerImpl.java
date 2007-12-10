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
import com.googlecode.instinct.internal.util.Suggest;
import java.util.Map;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

@Fix("Test this")
@SuppressWarnings({"unchecked"})
@Suggest("Consider removing inheritance, this would forsce you to save a typesafe form of subject, and not have to perform casts.")
public class MapCheckerImpl<K, V> extends ObjectCheckerImpl<Map<K, V>> implements MapChecker<K, V> {
    public MapCheckerImpl(final Map<K, V> subject) {
        super(subject);
    }

    public final void containsEntry(final Matcher<K> matcher, final Matcher<V> matcher1) {
        getAsserter().expectThat(subject, Matchers.hasEntry(matcher, matcher1));
    }

    public final void containsEntry(final K k, final V v) {
        getAsserter().expectThat(subject, Matchers.hasEntry(k, v));
    }

    public final void doesNotContainEntry(final Matcher<K> matcher, final Matcher<V> matcher1) {
        getAsserter().expectNotThat(subject, Matchers.hasEntry(matcher, matcher1));
    }

    public final void doesNotContainEntry(final K k, final V v) {
        getAsserter().expectNotThat(subject, Matchers.hasEntry(k, v));
    }

    public final void containsKey(final Matcher<K> matcher) {
        getAsserter().expectThat((Map<K, Object>) subject, Matchers.hasKey(matcher));
    }

    public final void containsKey(final K k) {
        getAsserter().expectThat((Map<K, Object>) subject, Matchers.hasKey(k));
    }

    public final void doesNotContainKey(final Matcher<K> matcher) {
        getAsserter().expectNotThat((Map<K, Object>) subject, Matchers.hasKey(matcher));
    }

    public final void doesNotContainKey(final K k) {
        getAsserter().expectNotThat((Map<K, Object>) subject, Matchers.hasKey(k));
    }

    public final void containsValue(final Matcher<V> matcher) {
        getAsserter().expectThat((Map<Object, V>) subject, Matchers.hasValue(matcher));
    }

    public final void containsValue(final V v) {
        getAsserter().expectThat((Map<Object, V>) subject, Matchers.hasValue(v));
    }

    public final void doesNotContainValue(final Matcher<V> matcher) {
        getAsserter().expectNotThat((Map<Object, V>) subject, Matchers.hasValue(matcher));
    }

    public final void doesNotContainValue(final V v) {
        getAsserter().expectNotThat((Map<Object, V>) subject, Matchers.hasValue(v));
    }

    public final void isOfSize(final int size) {
        getAsserter().expectThat(subject.size(), Matchers.equalTo(size));
    }

    public final void isEmpty() {
        getAsserter().expectThat(subject.isEmpty(),
                Matchers.describedAs("isEmpty() = <true>", Matchers.equalTo(true)));
    }

    public final void isNotEmpty() {
        getAsserter().expectThat(subject.isEmpty(),
                Matchers.describedAs("isEmpty() = <false>", Matchers.equalTo(false)));
    }
}
