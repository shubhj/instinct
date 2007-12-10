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

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class ArrayCheckerImpl<T> extends ObjectCheckerImpl<T[]> implements ArrayChecker<T> {

    public ArrayCheckerImpl(final T[] subject) {
        super(subject);
    }

    public final void isEmpty() {
        getAsserter().expectThat(subject.length == 0, Matchers.describedAs("length == 0", Matchers.equalTo(true)));
    }

    public final void notEmpty() {
        getAsserter().expectThat(subject.length != 0, Matchers.describedAs("length != 0", Matchers.equalTo(true)));
    }

    public final void isNotEmpty() {
        notEmpty();
    }

    public final void containsItem(final Matcher<T> matcher) {
        getAsserter().expectThat(subject, Matchers.hasItemInArray(matcher));
    }

    public final void containsItem(final T t) {
        getAsserter().expectThat(subject, Matchers.hasItemInArray(t));
    }

    public final void doesNotContainItem(final Matcher<T> matcher) {
        getAsserter().expectNotThat(subject, Matchers.hasItemInArray(matcher));
    }

    public final void doesNotContainItem(final T t) {
        getAsserter().expectNotThat(subject, Matchers.hasItemInArray(t));
    }

    public final void isOfSize(final int size) {
        getAsserter().expectThat(subject.length, Matchers.equalTo(size));
    }

//    public final void isOfSize(final int size) {
//        isOfSize(size);
//    }
}
