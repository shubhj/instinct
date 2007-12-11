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

import org.hamcrest.Matcher;

public interface ObjectChecker<T> {
    void isEqualTo(T t);

    void isNotEqualTo(T t);

    void isAnInstanceOf(Class<? extends T> cls);

    void isNotAnInstanceOf(Class<T> cls);

    void isOfType(Class<? extends T> cls);

    void isNotOfType(Class<T> cls);

    void isTheSameInstanceAs(T t);

    void isNotTheSameInstanceAs(T t);

    void isNull();

    void isNotNull();

    void hasToString(Matcher<String> matcher);

    void matches(Matcher<T>... matchers);

    void matches(Iterable<Matcher<? extends T>> iterable);

    void doesNotMatch(Matcher<T>... matchers);

    void doesNotMatch(Iterable<Matcher<? extends T>> iterable);

    void matchesAnyOf(Matcher<T>... matchers);

    void matchesAnyOf(Iterable<Matcher<? extends T>> iterable);

    void hasBeanProperty(String string);

    void hasBeanProperty(String string, Matcher<?> matcher);
}
