/*
 * Copyright 2008 Tom Adams
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
import fj.F;
import fj.data.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.hamcrest.core.IsEqual.equalTo;

public final class ExistentialListMatcher<T> extends TypeSafeMatcher<List<T>> {
    private final Matcher<T> elementMatcher;

    private ExistentialListMatcher(final Matcher<T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }

    @Override
    public boolean matchesSafely(final List<T> list) {
        return list.exists(new F<T, Boolean>() {
            public Boolean f(final T a) {
                return elementMatcher.matches(a);
            }
        });
    }

    public void describeTo(final Description description) {
        checkNotNull(description);
        description.appendText("a list containing ").appendDescriptionOf(elementMatcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<List<T>> hasItemInList(final Matcher<T> elementMatcher) {
        checkNotNull(elementMatcher);
        return new ExistentialListMatcher(elementMatcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<List<T>> hasItemInList(final T item) {
        checkNotNull(item);
        return new ExistentialListMatcher(equalTo(item));
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<List<T>> hasItemInList(final F<T, Boolean> matchingFunction) {
        checkNotNull(matchingFunction);
        return new ExistentialListMatcher(new MatchesFunction<T>(matchingFunction));
    }
}
