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

import java.util.Map;

// TODO Test this
public class MapCheckerImpl<K,V> extends ObjectCheckerImpl<Map<K,V>>
        implements MapChecker<K,V> {

    public MapCheckerImpl(Map<K, V> subject) {
        super(subject);
    }

    public final void containsEntry(org.hamcrest.Matcher<K> matcher, org.hamcrest.Matcher<V> matcher1) {
        getAsserter().expectThat(subject, Matchers.hasEntry(matcher, matcher1));
    }

    public final void containsEntry(K k, V v) {
        getAsserter().expectThat(subject, Matchers.hasEntry(k, v));
    }

    public final void notContainEntry(org.hamcrest.Matcher<K> matcher, org.hamcrest.Matcher<V> matcher1) {
        getAsserter().expectNotThat(subject, Matchers.hasEntry(matcher, matcher1));
    }

    public final void notContainEntry(K k, V v) {
        getAsserter().expectNotThat(subject, Matchers.hasEntry(k, v));
    }

    public final void containsKey(org.hamcrest.Matcher<K> matcher) {
        getAsserter().expectThat((Map<K,Object>) subject, Matchers.hasKey(matcher));
    }

    public final void containsKey(K k) {
        getAsserter().expectThat((Map<K,Object>) subject, Matchers.hasKey(k));
    }

    public final void notContainKey(org.hamcrest.Matcher<K> matcher) {
        getAsserter().expectNotThat((Map<K,Object>) subject, Matchers.hasKey(matcher));
    }

    public final void notContainKey(K k) {
        getAsserter().expectNotThat((Map<K,Object>) subject, Matchers.hasKey(k));
    }

    public final void containsValue(org.hamcrest.Matcher<V> matcher) {
        getAsserter().expectThat((Map<Object,V>) subject, Matchers.hasValue(matcher));
    }

    public final void containsValue(V v) {
        getAsserter().expectThat((Map<Object,V>) subject, Matchers.hasValue(v));
    }

    public final void notContainValue(org.hamcrest.Matcher<V> matcher) {
        getAsserter().expectNotThat((Map<Object,V>) subject, Matchers.hasValue(matcher));
    }

    public final void notContainValue(V v) {
        getAsserter().expectNotThat((Map<Object,V>) subject, Matchers.hasValue(v));
    }

    public final void isEmpty() {
        getAsserter().expectThat(subject.isEmpty(),
                Matchers.describedAs("isEmpty() = <true>", Matchers.equalTo(true)));
    }

    public final void notEmpty() {
        getAsserter().expectThat(subject.isEmpty(),
                Matchers.describedAs("isEmpty() = <false>", Matchers.equalTo(false)));
    }

    public final void hasSize(int size) {
        getAsserter().expectThat(subject.size(), Matchers.equalTo(size));
    }
}
