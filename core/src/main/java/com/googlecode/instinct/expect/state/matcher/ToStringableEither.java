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

package com.googlecode.instinct.expect.state.matcher;

import fj.data.Either;
import fj.data.Either.LeftProjection;
import fj.data.Either.RightProjection;
import fj.pre.Show;
import static fj.pre.Show.eitherShow;

public final class ToStringableEither<A, B> {
    private final Either<A, B> wrapped;

    private ToStringableEither(final Either<A, B> wrapped) {
        this.wrapped = wrapped;
    }

    public boolean isLeft() {
        return wrapped.isLeft();
    }

    public boolean isRight() {
        return wrapped.isRight();
    }

    @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
    public LeftProjection left() {
        return (LeftProjection<A, B>) wrapped.left();
    }

    @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
    public RightProjection right() {
        return (RightProjection<A, B>) wrapped.right();
    }

    @Override
    public String toString() {
        return eitherShow(Show.<A>anyShow(), Show.<B>anyShow()).showS(wrapped);
    }

    public static <A, B> ToStringableEither<A, B> toStringableEither(final Either<A, B> either) {
        return new ToStringableEither<A, B>(either);
    }
}
