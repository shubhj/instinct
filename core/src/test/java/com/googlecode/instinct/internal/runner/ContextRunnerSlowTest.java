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
import com.googlecode.instinct.internal.core.ContextClassImpl;
import static com.googlecode.instinct.internal.util.Fj.not;
import com.googlecode.instinct.test.InstinctTestCase;
import fj.F;
import static fj.Function.compose;

public final class ContextRunnerSlowTest extends InstinctTestCase {
    private ContextRunner runner;

    @Override
    public void setUpSubject() {
        runner = new StandardContextRunner();
    }

    public void testRunsContexts() {
        runContext(ContextContainerWithSetUpAndTearDown.AnEmbeddedPublicContext.class);
        runContext(ContextContainerWithConstructors.APublicConstructor.class);
        runContext(ContextWithSpecificationWithReturnType.class);
    }

    public void testInvalidConstructorsThrowConfigException() {
        checkInvalidConstructorsGivesFailedStatus(ContextContainerWithConstructors.AConstructorWithParameters.class);
    }

    private <T> void runContext(final Class<T> contextClass) {
        runner.run(new ContextClassImpl(contextClass));
    }

    private <T> void checkInvalidConstructorsGivesFailedStatus(final Class<T> cls) {
        final ContextResult result = runner.run(new ContextClassImpl(cls));
        expect.that(result.completedSuccessfully()).isFalse();
        expect.that(result.getSpecificationResults()).allItemsMatch(compose(not(), runSuccessful()));
    }

    private <T> F<SpecificationResult, Boolean> runSuccessful() {
        return new F<SpecificationResult, Boolean>() {
            public Boolean f(final SpecificationResult result) {
                return result.completedSuccessfully();
            }
        };
    }
}
