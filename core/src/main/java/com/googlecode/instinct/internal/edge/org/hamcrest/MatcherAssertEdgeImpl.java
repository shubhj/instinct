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

import com.googlecode.instinct.internal.util.Fix;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.not;

@Fix("Move this out of the edge package. And rename it.")
public final class MatcherAssertEdgeImpl implements MatcherAssertEdge {
    private final MatcherVerifier matcherVerifier = new MatcherVerifierImpl();

    public <T> void expectThat(final T t, final Matcher<T> matcher) {
        matcherVerifier.assertThat(t, matcher);
    }

    public <T> void expectThat(final T t, final Matcher<T> matcher, final MatcherDescriber describing) {
        matcherVerifier.assertThat(t, matcher, describing);
    }

    public <T> void expectNotThat(final T t, final Matcher<T> matcher) {
        expectThat(t, not(matcher));
    }

    public <T> void expectNotThat(final T t, final Matcher<T> matcher, final MatcherDescriber describing) {
        expectThat(t, not(matcher), describing);
    }
}
