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
package com.googlecode.instinct.integrate.junit4;

import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.edge.org.junit.runner.DescriptionEdge;
import com.googlecode.instinct.internal.edge.org.junit.runner.DescriptionEdgeImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunFailureStatus;
import com.googlecode.instinct.internal.util.exception.ExceptionFinder;
import com.googlecode.instinct.internal.util.exception.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import fj.Effect;
import fj.data.List;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public final class JUnit4SpecificationRunnerImpl implements JUnit4SpecificationRunner {
    private final DescriptionEdge descriptionEdge = new DescriptionEdgeImpl();
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final RunNotifier notifier;

    public JUnit4SpecificationRunnerImpl(final RunNotifier notifier) {
        checkNotNull(notifier);
        this.notifier = notifier;
    }

    public void run(final List<SpecificationMethod> specificationMethods) {
        checkNotNull(specificationMethods);
        specificationMethods.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod method) {
                runSpecification(method);
            }
        });
    }

    private void runSpecification(final SpecificationMethod specificationMethod) {
        final Description description = createDescription(specificationMethod);
        notifier.fireTestStarted(description);
        final SpecificationResult specificationResult = specificationMethod.run();
        if (specificationResult.completedSuccessfully()) {
            if (specificationResult.getStatus().getDetails().equals(PENDING)) {
                notifier.fireTestIgnored(description);
            } else {
                notifier.fireTestFinished(description);
            }
        } else {
            notifier.fireTestFailure(createFailure(description, specificationResult));
        }
    }

    private Description createDescription(final LifecycleMethod specificationMethod) {
        final String name = specificationMethod.getName();
        return descriptionEdge.createTestDescription(specificationMethod.getContextClass(), name);
    }

    private Failure createFailure(final Description description, final SpecificationResult specificationResult) {
        final Throwable rootCause =
                exceptionFinder.getRootCause(((SpecificationRunFailureStatus) specificationResult.getStatus()).getDetails());
        return objectFactory.create(Failure.class, description, rootCause);
    }
}
