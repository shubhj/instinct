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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import fj.F;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class MatchesFunction<T> extends TypeSafeMatcher<T> {
    private final F<T, Boolean> matchingFunction;

    public MatchesFunction(final F<T, Boolean> matchingFunction) {
        checkNotNull(matchingFunction);
        this.matchingFunction = matchingFunction;
    }

    @Suggest("It'd be nice to have the function able to print itself as a sting, optionally add a method that allows that?")
    public void describeTo(final Description description) {
        checkNotNull(description);
        description.appendText("matches the passed function");
    }

    @Override
    public boolean matchesSafely(final T item) {
        return matchingFunction.f(item);
    }
}
