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

import com.googlecode.instinct.expect.state.matcher.ToStringableEither;
import org.hamcrest.Matcher;

public interface EitherChecker<A, B> extends ObjectChecker<ToStringableEither<A, B>> {
    void isLeft();

    void isRight();

    void isLeft(final A left);

    void isRight(final B right);

    void isLeft(final Matcher<A> matcher);

    void isRight(final Matcher<B> matcher);
}
