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

package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings({"JUnitTestCaseInProductSource", "UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests",
        "JUnitTestCaseWithNonTrivialConstructors"})
public final class SpecificationTestCase extends TestCase {
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private TestResult result;
    private SpecificationMethod specificationMethod;

    public SpecificationTestCase(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        this.specificationMethod = specificationMethod;
    }

    @Override
    public void run(final TestResult result) {
        this.result = result;
        super.run(result);
    }

    @Override
    public void runBare() {
        try {
            runSpecification();
        } catch (EdgeException e) {
            exceptionFinder.rethrowRealError(e);
        }
    }

    @Override
    public String getName() {
        return specificationMethod.getName();
    }

    @SuppressWarnings({"CatchGenericClass"})
    // SUPPRESS IllegalCatch {
    private void runSpecification() {
        try {
            final SpecificationResult specificationResult = specificationMethod.run();
            processSpecificationResult(specificationResult);
        } catch (Throwable e) {
            result.addError(this, e);
        }
    }
    // } SUPPRESS IllegalCatch

    private void processSpecificationResult(final SpecificationResult specificationResult) {
        if (!specificationResult.completedSuccessfully()) {
            final SpecificationRunStatus status = specificationResult.getStatus();
            final Throwable error = (Throwable) status.getDetailedStatus();
            result.addFailure(this, new ChainableAssertionFailedError(exceptionFinder.getRootCause(error)));
        }
    }
}
