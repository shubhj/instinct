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

package com.googlecode.instinct.internal.aggregate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;

public final class ContextAggregatorSlowTest extends InstinctTestCase {
    public static final int EXPECTED_CONTEXTS = 35;
    private ContextAggregator aggregator;

    @Override
    public void setUpSubject() {
        aggregator = new ContextClassAggregatorImpl(ContextAggregatorSlowTest.class);
    }

    public void testFindsCorrectNumberOfContexts() {
        final JavaClassName[] contexts = aggregator.getContextNames();
        expect.that(contexts).hasSize(EXPECTED_CONTEXTS);
    }
}
