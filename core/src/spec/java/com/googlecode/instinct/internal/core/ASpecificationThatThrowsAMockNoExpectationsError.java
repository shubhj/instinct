/*
* Copyright 2006-2008 Tom Adams
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

package com.googlecode.instinct.internal.core;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.util.AggregatingException;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import fj.F;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
@RunWith(InstinctRunner.class)
public final class ASpecificationThatThrowsAMockNoExpectationsError {
    @Specification
    public void failsTheSpecEarlyWithOneError() {
        final ContextClass contextClass = new ContextClassImpl(MockVerificiationAndException.class);
        final ContextResult result = contextClass.run();
        expect.that(result.completedSuccessfully()).isFalse();
        final SpecificationResult specResult = result.getSpecificationResults().head();
        final AggregatingException exception =
                (AggregatingException) ((SpecificationRunFailureStatus) specResult.getStatus()).getDetails().getCause();
        expect.that(exception.getAggregatedErrors()).isOfSize(1);
        expect.that(exception.getAggregatedErrors()).contains(new F<Throwable, Boolean>() {
            public Boolean f(final Throwable throwable) {
                return throwable.getMessage().contains("no expectations specified");
            }
        });
    }

    private static final class MockVerificiationAndException {
        @Mock private Runnable mockRunnable;

        @Specification
        public void oneError() {
            mockRunnable.run();
            new Fails().fail();
        }
    }

    private static final class Fails {
        public void fail() {
            throw new RuntimeException("This should not be seen!");
        }
    }
}