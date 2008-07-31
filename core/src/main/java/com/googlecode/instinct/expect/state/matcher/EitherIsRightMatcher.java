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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;
import static org.hamcrest.core.IsEqual.equalTo;

public final class EitherIsRightMatcher<A, B> extends TypeSafeMatcher<ToStringableEither<A, B>> {
    private Matcher<ToStringableEither<A, B>> matcher;

    private EitherIsRightMatcher(final Matcher<ToStringableEither<A, B>> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final ToStringableEither<A, B> either) {
        return either.isRight() && matcher.matches(either.right().value());
    }

    public void describeTo(final Description description) {
        description.appendDescriptionOf(matcher).appendText(" on the right");
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isRight(final Matcher<B> elementMatcher) {
        checkNotNull(elementMatcher);
        return new EitherIsRightMatcher(elementMatcher);
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isRight(final B right) {
        checkNotNull(right);
        return new EitherIsRightMatcher(equalTo(right));
    }

    @SuppressWarnings({"unchecked"})
    @Factory
    public static <A, B> Matcher<ToStringableEither<A, B>> isRight() {
        return new EitherIsRightMatcher<A, B>(IsAnything.<ToStringableEither<A, B>>anything());
    }
}
