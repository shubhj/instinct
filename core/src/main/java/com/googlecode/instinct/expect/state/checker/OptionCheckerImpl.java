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

import com.googlecode.instinct.expect.state.matcher.OptionIsNoneMatcher;
import com.googlecode.instinct.expect.state.matcher.OptionIsSomeMatcher;
import com.googlecode.instinct.expect.state.matcher.ToStringableOption;
import org.hamcrest.Matcher;

public final class OptionCheckerImpl<T> extends ObjectCheckerImpl<ToStringableOption<T>> implements OptionChecker<T> {
    public OptionCheckerImpl(final ToStringableOption<T> subject) {
        super(subject);
    }

    public void isNone() {
        getAsserter().expectThat(subject, OptionIsNoneMatcher.<T>isNone());
    }

    public void isSome() {
        getAsserter().expectThat(subject, OptionIsSomeMatcher.<T>isSome());
    }

    public void isSome(final T some) {
        getAsserter().expectThat(subject, OptionIsSomeMatcher.<T>isSome(some));
    }

    public void isSome(final Matcher<T> matcher) {
        getAsserter().expectThat(subject, OptionIsSomeMatcher.<T>isSome(matcher));
    }
}
