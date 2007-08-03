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

import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matcher;

public interface ObjectChecker<T> {
    void equalTo(T t);

    void notEqualTo(T t);

    void instanceOf(Class<T> cls);

    void notInstanceOf(Class<T> cls);

    void sameInstanceAs(T t);

    void notSameInstanceAs(T t);

    void isNull();

    void notNull();

    void hasToString(Matcher<String> matcher);

    void matchesAllOf(Matcher<T>... matchers);

    void matchesAllOf(Iterable<Matcher<T>> iterable);

    void notMatchAllOf(Matcher<T>... matchers);

    void notMatchAllOf(Iterable<Matcher<T>> iterable);

    void matchesAnyOf(Matcher<T>... matchers);

    void matchesAnyOf(Iterable<Matcher<T>> iterable);

    void notMatchAnyOf(Matcher<T>... matchers);

    void notMatchAnyOf(Iterable<Matcher<T>> iterable);

    void hasBeanProperty(String string);

    @Suggest("Can we type this?")
    void hasBeanProperty(String string, Matcher matcher);
}
