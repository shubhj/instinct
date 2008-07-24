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

import com.googlecode.instinct.expect.state.describer.PropertyMatcherDescriber;
import com.googlecode.instinct.expect.state.describer.PropertyMatcherWithValueDescriber;
import static com.googlecode.instinct.expect.state.matcher.NoneOf.noneOf;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

// SUPPRESS VisibilityModifier|IllegalToken {
public class ObjectCheckerImpl<T> implements ObjectChecker<T> {
    protected final T subject;
    private final MatcherAssertEdge asserter = new MatcherAssertEdgeImpl();

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

    public final void isAnInstanceOf(final Class<?> type) {
        getAsserter().expectThat(subject, Matchers.instanceOf(type));
    }

    public final void isNotAnInstanceOf(final Class<?> type) {
        getAsserter().expectNotThat(subject, Matchers.instanceOf(type));
    }

    public final void isOfType(final Class<?> type) {
        isAnInstanceOf(type);
    }

    public final void isNotOfType(final Class<?> type) {
        isNotAnInstanceOf(type);
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

    public final void matches(final Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.allOf(matchers));
    }

    public final void matches(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectThat(subject, Matchers.allOf(iterable));
    }

    public final void matchesAnyOf(final Matcher<T>... matchers) {
        getAsserter().expectThat(subject, Matchers.anyOf(matchers));
    }

    public final void matchesAnyOf(final Iterable<Matcher<? extends T>> iterable) {
        getAsserter().expectThat(subject, Matchers.anyOf(iterable));
    }

    public final void doesNotMatchOnAllOf(final Matcher<T>... matchers) {
        getAsserter().expectThat(subject, noneOf(matchers));
    }

    public final void doesNotMatchOnAllOf(final Iterable<Matcher<? extends T>> matchers) {
        getAsserter().expectThat(subject, noneOf(matchers));
    }

    public final void hasBeanProperty(final String propertyName, final Class<?> propertyType) {
        getAsserter().expectThat(subject, Matchers.hasProperty(propertyName), new PropertyMatcherDescriber<T>(subject, propertyName, propertyType));
    }

    public final void hasBeanPropertyWithValue(final String propertyName, final Class<?> propertyType, final Matcher<?> matcher) {
        getAsserter().expectThat(subject, Matchers.hasProperty(propertyName, matcher),
                new PropertyMatcherWithValueDescriber<T>(subject, propertyName, propertyType, matcher));
    }

    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
    @Override
    public final boolean equals(final Object obj) {
        throw new UnsupportedOperationException("Equality on checkers is not supported, you probably want isEqualTo() instead.");
    }
}
// } SUPPRESS VisibilityModifier|IllegalToken
