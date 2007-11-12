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

public interface ObjectChecker<T> {
    void equalTo(T t);

    void isEqualTo(T t);

    void notEqualTo(T t);

    void instanceOf(Class<? extends T> cls);

    void isAnInstanceOf(Class<? extends T> cls);

    void isOfType(Class<? extends T> cls);

    void ofType(Class<? extends T> cls);

    void notInstanceOf(Class<T> cls);

    void isNotAnInstanceOf(Class<T> cls);

    void isNotOfType(Class<T> cls);

    void notOfType(Class<T> cls);

    void sameInstanceAs(T t);

    void notSameInstanceAs(T t);

    void isNull();

    void isNotNull();

    void hasToString(Matcher<String> matcher);

    void matchesAllOf(Matcher<T>... matchers);

    void matchesAllOf(Iterable<Matcher<? extends T>> iterable);

    void notMatchAllOf(Matcher<T>... matchers);

    void notMatchAllOf(Iterable<Matcher<? extends T>> iterable);

    void matchesAnyOf(Matcher<T>... matchers);

    void matchesAnyOf(Iterable<Matcher<? extends T>> iterable);

    void notMatchAnyOf(Matcher<T>... matchers);

    void notMatchAnyOf(Iterable<Matcher<? extends T>> iterable);

    void hasBeanProperty(String string);

    void hasBeanProperty(String string, Matcher<?> matcher);
}
