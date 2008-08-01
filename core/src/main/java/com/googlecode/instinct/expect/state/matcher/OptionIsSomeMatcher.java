/*
* Copyright 2006-2008 Tom Adams
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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import fj.data.Option;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsAnything.anything;

public final class OptionIsSomeMatcher<T> extends TypeSafeMatcher<Option<T>> {
    private final Matcher<T> matcher;

    private OptionIsSomeMatcher(final Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final Option<T> item) {
        return item.isSome() && matcher.matches(item.some());
    }

    public void describeTo(final Description description) {
        description.appendText("a some option containing ").appendDescriptionOf(matcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<Option<T>> isSome(final Matcher<T> elementMatcher) {
        checkNotNull(elementMatcher);
        return new OptionIsSomeMatcher(elementMatcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<Option<T>> isSome(final T item) {
        checkNotNull(item);
        return new OptionIsSomeMatcher(equalTo(item));
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<Option<T>> isSome() {
        return new OptionIsSomeMatcher<T>((Matcher<T>) anything());
    }
}
