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
import com.googlecode.instinct.expect.behaviour.Mocker;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import fj.Effect;
import fj.F;
import org.jmock.api.ExpectationError;

@SuppressWarnings({"StringContatenationInLoop", "OverlyCoupledClass", "UnusedDeclaration"})
public final class SpecificationRunnerSlowTest extends InstinctTestCase {
    @Subject(implementation = SpecificationRunnerImpl.class) private SpecificationRunner runner;

    public void testRunWithSuccess() {
        checkContextsRunWithoutError(ContextContainerWithSetUpAndTearDown.class);
        checkContextsRunWithoutError(ContextWithSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithBeforeSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextWithAfterSpecificationWithReturnType.class);
        checkContextsRunWithoutError(ContextContainerWithConstructors.APrivateConstructor.class);
        checkContextsRunWithoutError(ContextContainerWithConstructors.APackageLocalConstructor.class);
        checkContextsRunWithoutError(ContextContainerWithConstructors.AProtectedConstructor.class);
    }

    public void testInvalidMethodsBarf() {
        checkInvalidMethodsBarf(ContextWithSpecificationMethodContainingParameter.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedAfterSpecification2.class);
        checkInvalidMethodsBarf(ContextWithInvalidlyMarkedBeforeSpecification2.class);
    }

    public void testWiresInStubsMocksAndDummiesForUseSpecifications() {
        final SpecificationMethod method = getSpecificationMethod(ContextWithAutoWiredFields.class, "doSomethingWithAutoWiredDoubles");
        final SpecificationResult result = runner.run(method);
        expect.that(result.completedSuccessfully()).isTrue();
    }

    public void testVerifiesAutoWiredMocks() {
        final SpecificationMethod method = getSpecificationMethod(ContextWithAutoWiredFields.class, "autoWiredMocksFailToBeCalled");
        final SpecificationResult result = runner.run(method);
        // Note. Need to reset the mocker or the error will be caught in the InstinctTestCase super class.
        Mocker.reset();
        expect.that(result.completedSuccessfully()).isFalse();
        final Object detailedStatus = result.getStatus().getDetailedStatus();
        expect.that(detailedStatus).isAnInstanceOf(SpecificationFailureException.class);
        final Throwable failureCause = ((Throwable) detailedStatus).getCause();
        expect.that(failureCause).isAnInstanceOf(ExpectationError.class);
        expect.that(failureCause.getMessage()).matchesRegex("not all expectations were satisfied");
    }

    private <T> SpecificationMethod getSpecificationMethod(final Class<T> contextClass, final Object specMethodName) {
        return new ContextClassImpl(contextClass).getSpecificationMethods().filter(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod a) {
                return a.getName().equals(specMethodName);
            }
        }).head();
    }

    private <T> void checkContextsRunWithoutError(final Class<T> contextClass) {
        new ContextClassImpl(contextClass).getSpecificationMethods().foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod a) {
                final SpecificationResult result = runner.run(a);
                expect.that(result.completedSuccessfully()).isTrue();
            }
        });
    }

    private <T> void checkInvalidMethodsBarf(final Class<T> contextClass) {
        new ContextClassImpl(contextClass).getSpecificationMethods().foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod a) {
                final SpecificationResult specificationResult = runner.run(a);
                expect.that(specificationResult.completedSuccessfully()).isFalse();
            }
        });
    }
}
