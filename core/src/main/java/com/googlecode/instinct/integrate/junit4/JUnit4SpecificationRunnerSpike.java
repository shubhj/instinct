/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.integrate.junit4;

public class JUnit4SpecificationRunnerSpike {
//    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
//
//    public void runSpecifications(final Collection<SpecificationMethod> specificationMethods, final RunNotifier notifier) {
//        for (final SpecificationMethod specificationMethod : specificationMethods) {
//            setUpAndRunSpecification(notifier, specificationMethod);
//        }
//    }
//
//    public void setUpAndRunSpecification(final RunNotifier notifier, final SpecificationMethod specificationMethod) {
//        final Description description = Description.createTestDescription(specificationMethod.getSpecificationMethod().getDeclaringClass(),
//                specificationMethod.getName());
//        notifier.fireTestStarted(description);
//        final SpecificationResult specificationResult = specificationMethod.run();
//        if (specificationResult.completedSuccessfully()) {
//            notifier.fireTestFinished(description);
//        } else {
//            notifier.fireTestFailure(createFailure(description, specificationResult));
//        }
//    }
//
//    private Failure createFailure(final Description description, final SpecificationResult specificationResult) {
//        final Throwable rootCause = exceptionFinder.getRootCause((Throwable) specificationResult.getStatus().getDetailedStatus());
//        return new Failure(description, rootCause);
//    }
}
