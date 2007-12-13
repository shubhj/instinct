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

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matcher;

@Suggest("Remove the dependency on the Hamcrest StringDescription class.")
public final class HamcrestMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherUtil<T> matcherUtil = new CommonMatcherUtilImpl<T>();
    private final T actual;
    private final Matcher<T> matcher;

    public HamcrestMatcherDescriber(final T actual, final Matcher<T> matcher) {
        this.actual = actual;
        this.matcher = matcher;
    }

    public String describe() {
        final StringBuilder builder = new StringBuilder();
        builder.append(newLine()).
                    append("Expected: ").append(getExpectation()).
                append(newLine()).
                    append(createFiveSpaces()).append("got: ").append(getValue()).
                append(newLine());
        return builder.toString();
    }

    private String createFiveSpaces() {
        return matcherUtil.space(5);
    }

    private String newLine() {
        return matcherUtil.newLine();
    }

    private String getValue() {
        return matcherUtil.describeValue(actual);
    }

    private String getExpectation() {
        return matcherUtil.describeExpectation(matcher);
    }
}
