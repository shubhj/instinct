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

package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import fj.data.Option;

public final class SpecificationFailureMessageBuilderImplSlowTest extends InstinctTestCase {
    private SpecificationFailureMessageBuilder failureMessageBuilder;

    @Override
    public void setUpSubject() {
        failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();
    }

    public void testCreatesStackTracesFromSpecificationFailures() {
        final SpecificationFailureException failure = new SpecificationFailureException("Huzzah!", new RuntimeException("Huzzah!"));
        final String stack = failureMessageBuilder.buildMessage(new SpecificationRunFailureStatus(failure, Option.<Throwable>none()));
        expect.that(stack).isNotEmpty();
    }
}
