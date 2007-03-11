/*
 * Copyright 2006-2007 Ben Warren
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

package com.googlecode.instinct.expect.check;

import org.hamcrest.Matchers;

// TODO Test this
public class DoubleCheckerImpl extends ComparableCheckerImpl<Double> implements DoubleChecker {

    public DoubleCheckerImpl(Double subject) {
        super(subject);
    }

    public final void closeTo(double value, double delta) {
        getAsserter().expectThat(subject, Matchers.closeTo(value, delta));
    }

    public final void notCloseTo(double value, double delta) {
        getAsserter().expectNotThat(subject, Matchers.closeTo(value, delta));
    }
}
