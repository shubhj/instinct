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

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl;
import com.googlecode.instinct.internal.util.Fix;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

// SUPPRESS VisibilityModifier|IllegalToken {
@Fix("Test this.")
public class ObjectCheckerImpl<T> implements ObjectChecker<T> {
    @SuppressWarnings({"ProtectedField"})
    protected final T subject;
    private MatcherAssertEdge asserter = new MatcherAssertEdgeImpl();

    public ObjectCheckerImpl(final T subject) {
        this.subject = subject;
    }

    protected final MatcherAssertEdge getAsserter() {
        return asserter;
    }

    public final void isEqualTo(final T t) {
        getAsserter().expectThat(subject, Matchers.equalTo(t));

    }

    public final void isNotEqualTo(final T t) {
        getAsserter().expectNotThat(subject, Matchers.equalTo(t));
    }

    @Override
    public final boolean equals(final Object obj) {
        throw new UnsupportedOperationException("Equality on checkers is not supported, you probably want isEqualTo() instead.");
    }

    public final void isAnInstanceOf(final Class<? extends T> cls) {
        getAsserter().expectThat(subject, Matchers.instanceOf(cls));
    }

    public final void isOfType(final Class<? extends T> cls) {
        isAnInstanceOf(cls);
    }

    public final void notInstanceOf(final Class<T> cls) {
        getAsserter().expectNotThat(subject, Matchers.instanceOf(cls));
    }

    public final void isNotAnInstanceOf(final Class<T> cls) {
        notInstanceOf(cls);
    }

    public final void isNotOfType(final Class<T> cls) {
        notInstanceOf(cls);
    }

    public final void isTheSameInstanceAs(final T t) {
        getAsserter().expectThat(subject, Matchers.sameInstance(t));
    }

    public final void isNotTheSameInstanceAs(final T t) {
        getAsserter().expectNotThat(subject, Matchers.sameInstance(t));
    }

    public final void isNull() {
        getAsserter().expectThat(subject, Matchers.nullValue());
    }

    public final void isNotNull() {
        getAsserter().expectThat(subject, Matchers.notNullValue());
    }

    public final void hasToString(final Matcher<String> matcher) {
        getAsserter().expectThat(subject, Matchers.hasToString(matcher));
    }

    public final void matchesAllOf(final Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.allOf(matchers));
    }

    public final void matchesAllOf(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectThat(subject, Matchers.allOf(iterable));
    }

    public final void doesNotMatchAllOf(final Matcher<T>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.allOf(matchers));
    }

    public final void doesNotMatchAllOf(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectNotThat(subject, Matchers.allOf(iterable));
    }

    public final void matchesAnyOf(final Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.anyOf(matchers));
    }

    public final void matchesAnyOf(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectThat(subject, Matchers.anyOf(iterable));
    }

    public final void doesNotMatchAnyOf(final Matcher<T>... matchers) {
        getAsserter().expectNotThat(subject, Matchers.anyOf(matchers));
    }

    public final void doesNotMatchAnyOf(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectNotThat(subject, Matchers.anyOf(iterable));
    }

    public final void hasBeanProperty(final String string) {
        getAsserter().expectThat(subject, Matchers.hasProperty(string));
    }

    public final void hasBeanProperty(final String string, final Matcher<?> matcher) {
        getAsserter().expectThat(subject, Matchers.hasProperty(string, matcher));
    }
}
// } SUPPRESS VisibilityModifier|IllegalToken
