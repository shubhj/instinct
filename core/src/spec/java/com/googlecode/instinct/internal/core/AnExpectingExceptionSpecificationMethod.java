/*
* Copyright 2006-2008 Workingmouse
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
import com.googlecode.instinct.internal.runner.ContextWithExpectedFailures;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.marker.annotate.Specification;
import fj.F;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
@RunWith(InstinctRunner.class)
public final class AnExpectingExceptionSpecificationMethod {
    @Specification
    public void testExpectedFailuresDoNotFailSpec() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithoutMessage");
        final SpecificationResult specificationResult = spec.run();
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    @Specification
    public void testExpectedFailureWithDifferentExceptionFails() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithDifferentException");
        expect.that(spec.run().completedSuccessfully()).isFalse();
    }

    @Specification
    public void testExpectedFailuresWithExpectedMessageSucceeds() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithMessage");
        expect.that(spec.run().completedSuccessfully()).isTrue();
    }

    @Specification
    public void testExpectedFailuresWithDifferentMessageCausesFailure() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithDifferentMessage");
        expect.that(spec.run().completedSuccessfully()).isFalse();
    }

    @Specification
    public void testExpectedFailureButDoesNotFail() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "markedAsFailureButDoesNot");
        expect.that(spec.run().completedSuccessfully()).isFalse();
    }

    @Specification
    public void testExpectedFailureWithEdgeException() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithEdgeException");
        expect.that(spec.run().completedSuccessfully()).isTrue();
    }

    @Specification
    public void testExpectedFailureWithEdgeExceptionWithNestedInvocationTargetException() {
        final SpecificationMethod spec =
                getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithEdgeExceptionWithInvocationTargetException");
        expect.that(spec.run().completedSuccessfully()).isTrue();
    }

    @Specification
    public void testExpectedFailureWithIndexOutOfBoundsException() {
        final SpecificationMethod spec = getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithIndexOutOfBoundsException");
        expect.that(spec.run().completedSuccessfully()).isTrue();
    }

    private <T> SpecificationMethod getSpecificationMethod(final Class<T> contextClass, final Object specMethodName) {
        return new ContextClassImpl(contextClass).getSpecificationMethods().filter(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod a) {
                return a.getName().equals(specMethodName);
            }
        }).head();
    }
}