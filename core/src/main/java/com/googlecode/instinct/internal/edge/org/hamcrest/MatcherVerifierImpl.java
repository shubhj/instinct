/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.edge.org.hamcrest;

import com.googlecode.instinct.expect.state.describer.HamcrestMatcherDescriber;
import org.hamcrest.Matcher;

public final class MatcherVerifierImpl implements MatcherVerifier {

    public <T> void assertThat(final T actual, final Matcher<T> matcher, final MatcherDescriber describing) {
        if (!matcher.matches(actual)) {
            throw new AssertionError(describing.describe());
        }
    }

    public <T> void assertThat(final T actual, final Matcher<T> matcher) {
        assertThat(actual, matcher, new HamcrestMatcherDescriber<T>(actual, matcher));
    }
}

