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

package com.googlecode.instinct.expect.state.matcher;

import java.util.Arrays;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public final class NoneOf<T> extends BaseMatcher<T> {
    private final Iterable<Matcher<? extends T>> matchers;

    private NoneOf(final Iterable<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    public boolean matches(final Object o) {
        for (final Matcher<? extends T> matcher : matchers) {
            if (matcher.matches(o)) {
                return false;
            }
        }
        return true;
    }

    public void describeTo(final Description description) {
        description.appendList("(not ",", not ",")", matchers);
    }

    @Factory
    public static <T> Matcher<T> noneOf(final Matcher<? extends T>... matchers) {
        return noneOf(Arrays.<Matcher<? extends T>>asList(matchers));
    }

    @Factory
    public static <T> Matcher<T> noneOf(final Iterable<Matcher<? extends T>> matchers) {
        return new NoneOf<T>(matchers);
    }
}
