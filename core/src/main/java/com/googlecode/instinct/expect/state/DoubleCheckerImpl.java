/*
 * Copyright 2006-2007 Tom Adams
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

package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.internal.util.Fix;
import org.hamcrest.Matchers;

@Fix("Test this")
public class DoubleCheckerImpl extends ComparableCheckerImpl<Double> implements DoubleChecker {
    public DoubleCheckerImpl(final Double subject) {
        super(subject);
    }

    public final void isCloseTo(final double value, final double delta) {
        getAsserter().expectThat(subject, Matchers.closeTo(value, delta));
    }

    public final void isNotCloseTo(final double value, final double delta) {
        getAsserter().expectNotThat(subject, Matchers.closeTo(value, delta));
    }
}
