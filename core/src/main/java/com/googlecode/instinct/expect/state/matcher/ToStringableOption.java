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

import fj.data.Option;
import fj.pre.Show;
import static fj.pre.Show.optionShow;

public final class ToStringableOption<A> {
    private final Option<A> wrapped;

    private ToStringableOption(final Option<A> wrapped) {
        this.wrapped = wrapped;
    }

    public boolean isNone() {
        return wrapped.isNone();
    }

    public boolean isSome() {
        return wrapped.isSome();
    }

    public A some() {
        return wrapped.some();
    }

    @Override
    public String toString() {
        return optionShow(Show.<A>anyShow()).showS(wrapped);
    }

    public static <A> ToStringableOption<A> toStringableOption(final Option<A> either) {
        return new ToStringableOption<A>(either);
    }
}
