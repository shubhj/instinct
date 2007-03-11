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

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl;
import org.hamcrest.Matchers;

// TODO Test this
public class ObjectCheckerImpl<T> implements ObjectChecker<T> {

    protected final T subject;
    private MatcherAssertEdge asserter = new MatcherAssertEdgeImpl();

    public ObjectCheckerImpl(T subject) {
        this.subject = subject;
    }

    public final void equalTo(T t) {
        getAsserter().expectThat(subject, Matchers.equalTo(t));
    }

    public final void notEqualTo(T t) {
        getAsserter().expectNotThat(subject, Matchers.equalTo(t));
    }

    public final void instanceOf(Class<T> aClass) {
        getAsserter().expectThat(subject, Matchers.instanceOf(aClass));
    }

    public final void notInstanceOf(Class<T> aClass) {
        getAsserter().expectNotThat(subject, Matchers.instanceOf(aClass));
    }

    public final void sameInstanceAs(T t) {
        getAsserter().expectThat(subject, Matchers.sameInstance(t));
    }

    public final void notSameInstanceAs(T t) {
        getAsserter().expectNotThat(subject, Matchers.sameInstance(t));
    }

    public final void isNull() {
        getAsserter().expectThat(subject, Matchers.nullValue());
    }

    public final void notNull() {
        getAsserter().expectThat(subject, Matchers.notNullValue());
    }

    public final void hasToString(org.hamcrest.Matcher<java.lang.String> matcher) {
        getAsserter().expectThat(subject, Matchers.hasToString(matcher));
    }

    public final void matchesAllOf(org.hamcrest.Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.allOf(matchers));
    }

    public final void matchesAllOf(java.lang.Iterable<org.hamcrest.Matcher<T>> iterable) {
        getAsserter().expectThat(subject, Matchers.allOf(iterable));
    }

    public final void notMatchAllOf(org.hamcrest.Matcher<T>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.allOf(matchers));
    }

    public final void notMatchAllOf(java.lang.Iterable<org.hamcrest.Matcher<T>> iterable) {
        getAsserter().expectNotThat(subject, Matchers.allOf(iterable));
    }

    public final void matchesAnyOf(org.hamcrest.Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.anyOf(matchers));
    }

    public final void matchesAnyOf(java.lang.Iterable<org.hamcrest.Matcher<T>> iterable) {
        getAsserter().expectThat(subject, Matchers.anyOf(iterable));
    }

    public final void notMatchAnyOf(org.hamcrest.Matcher<T>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.anyOf(matchers));
    }

    public final void notMatchAnyOf(java.lang.Iterable<org.hamcrest.Matcher<T>> iterable) {
        getAsserter().expectNotThat(subject, Matchers.anyOf(iterable));
    }

    public final void hasBeanProperty(java.lang.String string) {
        getAsserter().expectThat(subject, Matchers.hasProperty(string));
    }

    public final void hasBeanProperty(java.lang.String string, org.hamcrest.Matcher matcher) {
        getAsserter().expectThat(subject, Matchers.hasProperty(string, matcher));
    }

    protected final MatcherAssertEdge getAsserter() {
        return asserter;
    }
}
