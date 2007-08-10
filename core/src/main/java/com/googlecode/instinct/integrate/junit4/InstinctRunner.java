/*
 * Copyright 2006-2007 Chris Myers, Workingmouse
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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.marker.annotate.ContextClasses;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class InstinctRunner extends Runner {
    private Set<SpecificationMethod> methods = new HashSet<SpecificationMethod>();
    private final Description suiteDescription;

    public InstinctRunner(final Class<?> cls) throws InitializationError {
        createSpecifications(getAnnotatedClasses(cls));
        suiteDescription = Description.createSuiteDescription("Instinct Specification Suite for " + cls.getName());
    }

    @Override
    public final Description getDescription() {
        return suiteDescription;
    }

    @Override
    public final void run(final RunNotifier notifier) {
        notifier.fireTestRunStarted(suiteDescription);
        for (final SpecificationMethod specificationMethod : methods) {
            setUpAndRunSpecification(notifier, specificationMethod);
        }
    }

    private void setUpAndRunSpecification(final RunNotifier notifier, final SpecificationMethod specificationMethod) {
        final Description description = Description.createTestDescription(specificationMethod.getSpecificationMethod().getDeclaringClass(),
                specificationMethod.getName());
        notifier.fireTestStarted(description);
        final SpecificationResult specificationResult = specificationMethod.run();
        if (specificationResult.completedSuccessfully()) {
            notifier.fireTestFinished(description);
        } else {
            notifier.fireTestFailure(new Failure(description, (Throwable) specificationResult.getStatus().getDetailedStatus()));
        }
    }

    private void createSpecifications(final Class<?>[] annotatedClasses) {
        for (final Class<?> annotatedClass : annotatedClasses) {
            final ContextClass contextClass = new ContextClassImpl(annotatedClass);
            final Collection<SpecificationMethod> specificationMethods = contextClass.buildSpecificationMethods();
            methods.addAll(specificationMethods);
        }
    }

    private static Class<?>[] getAnnotatedClasses(final Class<?> cls) {
        final ContextClasses specificationClass = cls.getAnnotation(ContextClasses.class);
        return specificationClass.value();
    }
}
