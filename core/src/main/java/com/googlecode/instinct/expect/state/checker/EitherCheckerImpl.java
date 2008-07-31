/*
 * Copyright 2006-2008 Workingmouse
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

import com.googlecode.instinct.expect.state.matcher.EitherIsLeftMatcher;
import com.googlecode.instinct.expect.state.matcher.EitherIsRightMatcher;
import com.googlecode.instinct.expect.state.matcher.ToStringableEither;
import org.hamcrest.Matcher;

public final class EitherCheckerImpl<A, B> extends ObjectCheckerImpl<ToStringableEither<A, B>> implements EitherChecker<A, B> {
    public EitherCheckerImpl(final ToStringableEither<A, B> subject) {
        super(subject);
    }

    public void isLeft() {
        getAsserter().expectThat(subject, EitherIsLeftMatcher.<A, B>isLeft());
    }

    public void isRight() {
        getAsserter().expectThat(subject, EitherIsRightMatcher.<A, B>isRight());
    }

    public void isLeft(final A left) {
        getAsserter().expectThat(subject, EitherIsLeftMatcher.<A, B>isLeft(left));
    }

    public void isRight(final B right) {
        getAsserter().expectThat(subject, EitherIsRightMatcher.<A, B>isRight(right));
    }

    public void isLeft(final Matcher<A> matcher) {
        getAsserter().expectThat(subject, EitherIsLeftMatcher.<A, B>isLeft(matcher));
    }

    public void isRight(final Matcher<B> matcher) {
        getAsserter().expectThat(subject, EitherIsRightMatcher.<A, B>isRight(matcher));
    }
}
