/*
* Copyright 2006-2008 Nick Partridge
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

import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;

public final class EitherIsLeftMatcher<A, B> extends TypeSafeMatcher<ToStringableEither<A, B>> {
    private Matcher<ToStringableEither<A, B>> matcher;

    private EitherIsLeftMatcher(final Matcher<ToStringableEither<A, B>> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final ToStringableEither<A, B> either) {
        return either.isLeft() && matcher.matches(either.left().value());
    }

    public void describeTo(final Description description) {
        description.appendDescriptionOf(matcher).appendText(" on the left");
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isLeft(final Matcher<A> elementMatcher) {
        checkNotNull(elementMatcher);
        return new EitherIsLeftMatcher(elementMatcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isLeft(final A left) {
        checkNotNull(left);
        return new EitherIsLeftMatcher(equalTo(left));
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isLeft() {
        return new EitherIsLeftMatcher<A, B>(IsAnything.<ToStringableEither<A, B>>anything());
    }
}
