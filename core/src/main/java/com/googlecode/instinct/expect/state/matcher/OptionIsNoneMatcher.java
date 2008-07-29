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

import fj.data.Option;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class OptionIsNoneMatcher<T> extends TypeSafeMatcher<Option<T>> {
    private OptionIsNoneMatcher() {
    }

    @Override
    public boolean matchesSafely(final Option<T> item) {
        return item.isNone();
    }

    public void describeTo(final Description description) {
        description.appendText("a none option");
    }

    @Factory
    public static <T> Matcher<Option<T>> isNone() {
        return new OptionIsNoneMatcher<T>();
    }
}
