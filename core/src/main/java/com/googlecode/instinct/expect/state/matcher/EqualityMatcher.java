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

import fj.data.List;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Note. Use this instead of {@link org.hamcrest.core.IsEqual} as it is aware of Functional Java types.
 */
@SuppressWarnings({"UnnecessaryFullyQualifiedName"})
public final class EqualityMatcher<T> extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    private EqualityMatcher(final Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(final Object item) {
        return matcher.matches(item);
    }

    public void describeTo(final Description description) {
        description.appendDescriptionOf(matcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <T> Matcher<T> equalTo(final T item) {
        if (item instanceof List) {
            return new EqualityMatcher(ListEqualityMatcher.equalTo((List<Object>) item));
        } else {
            return new EqualityMatcher(IsEqual.equalTo(item));
        }
    }
}
