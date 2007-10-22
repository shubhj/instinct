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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.LifecycleMethodImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.core.SpecificationMethodImpl;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;

@SuppressWarnings({"StringContatenationInLoop", "OverlyCoupledClass"})
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

    public void testExpectedFailuresSucceed() {
        final SpecificationResult specificationResult = runner.run(getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithoutMessage"));
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    public void testExpectedFailureWithDifferentExceptionFails() {
        final SpecificationResult specificationResult = runner.run(
                getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithDifferentException"));
        expect.that(specificationResult.completedSuccessfully()).isFalse();
    }

    public void testExpectedFailuresWithExpectedMessageSucceeds() {
        final SpecificationResult specificationResult = runner.run(getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithMessage"));
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    public void testExpectedFailuresWithDifferentMessageCausesFailure() {
        final SpecificationResult specificationResult = runner.run(
                getSpecificationMethod(ContextWithExpectedFailures.class, "failsWithDifferentMessage"));
        expect.that(specificationResult.completedSuccessfully()).isFalse();
    }

    public void testExpectedFailureButDoesNotFail() {
        final SpecificationResult specificationResult = runner.run(
                getSpecificationMethod(ContextWithExpectedFailures.class, "markedAsFailureButDoesNot"));
        expect.that(specificationResult.completedSuccessfully()).isFalse();
    }

    public void testExpectedFailureWithEdgeException() {
        final SpecificationResult specificationResult = runner.run(
                getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithEdgeException"));
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    public void testExpectedFailureWithEdgeExceptionWithNestedInvocationTargetException() {
        final SpecificationResult specificationResult = runner.run(
                getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithEdgeExceptionWithInvocationTargetException"));
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    public void testExpectedFailureWithIndexOutOfBoundsException() {
        final SpecificationMethod method = getSpecificationMethod(ContextWithExpectedFailures.class, "expectedFailureWithIndexOutOfBoundsException");
        final SpecificationResult specificationResult = runner.run(method);
        expect.that(specificationResult.completedSuccessfully()).isTrue();
    }

    public void testWiresInStubsMocksAndDummiesForUseSpecifications() {
        final SpecificationMethod method = getSpecificationMethod(ContextWithAutoWiredFields.class, "doSomethingWithAutoWiredDoubles");
        final SpecificationResult result = runner.run(method);
        expect.that(result.completedSuccessfully()).isTrue();
    }

    private <T> SpecificationMethod getSpecificationMethod(final Class<T> contextClass, final String specMethodName) {
        return new SpecificationMethodImpl(new LifecycleMethodImpl(getMethod(contextClass, specMethodName)), Collections.<LifecycleMethod>emptyList(),
                Collections.<LifecycleMethod>emptyList());
    }

    private <T> void checkContextsRunWithoutError(final Class<T> contextClass) {
        final Collection<SpecificationMethod> specificationMethods = findSpecificationMethods(contextClass);
        for (final SpecificationMethod specificationMethod : specificationMethods) {
            final SpecificationResult result = runner.run(specificationMethod);
            expect.that(result.completedSuccessfully()).isTrue();
        }
    }

    private <T> void checkInvalidMethodsBarf(final Class<T> contextClass) {
        final Collection<SpecificationMethod> specificationMethods = findSpecificationMethods(contextClass);
        for (final SpecificationMethod specificationMethod : specificationMethods) {
            final SpecificationResult specificationResult = runner.run(specificationMethod);
            assertFalse("Spec " + specificationMethod.getName() + " should have failed", specificationResult.completedSuccessfully());
        }
    }

    private <T> Collection<SpecificationMethod> findSpecificationMethods(final Class<T> cls) {
        final Collection<SpecificationMethod> specs = new ArrayList<SpecificationMethod>();
        final ContextClass contextClass = new ContextClassImpl(cls);
        final Collection<LifecycleMethod> specificationMethods = contextClass.getSpecificationMethods();
        for (final LifecycleMethod specificationMethod : specificationMethods) {
            final SpecificationMethod spec = new SpecificationMethodImpl(specificationMethod, contextClass.getBeforeSpecificationMethods(),
                    contextClass.getAfterSpecificationMethods());
            specs.add(spec);
        }
        return specs;
    }
}
