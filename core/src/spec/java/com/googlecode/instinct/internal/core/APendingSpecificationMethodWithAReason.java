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
import com.googlecode.instinct.internal.runner.SpecificationRunPendingStatus;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import java.lang.reflect.Method;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class APendingSpecificationMethodWithAReason {
    @Subject(auto = false) private PendingSpecificationMethod pendingMethod;
    @Stub List<LifecycleMethod> beforeSpecificationMethods;
    @Stub List<LifecycleMethod> afterSpecificationMethods;

    @BeforeSpecification
    public void before() {
        final Method method = getDeclaredMethod(PendingSpecificationWithReason.class, "aPendingMethod");
        pendingMethod =
                new PendingSpecificationMethod(PendingSpecificationWithReason.class, method, beforeSpecificationMethods, afterSpecificationMethods);
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(PendingSpecificationMethod.class, SpecificationMethod.class);
    }

    @Specification
    public void hasANameThatComesFromTheUnderlyingMethodWithPendingAnnotation() {
        expect.that(pendingMethod.getName()).isEqualTo("aPendingMethod [PENDING - Haven't done it yet]");
    }

    @Specification
    public void exposesTheContextClass() {
        expect.that(pendingMethod.getContextClass().equals(PendingSpecificationWithReason.class)).isTrue();
    }

    @Specification
    public void pullsThePendingReasonOffTheAnnotation() {
        final String reason = pendingMethod.getPendingReason();
        expect.that(reason).isEqualTo("Haven't done it yet");
    }

    @Specification
    public void includesThePendingDescriptionInTheNameOfTheResult() {
        expect.that(pendingMethod.run().getSpecificationMethod().getName()).isEqualTo("aPendingMethod [PENDING - Haven't done it yet]");
    }

    @Specification
    public void hasAPendingStatus() {
        expect.that(pendingMethod.run().getStatus()).isOfType(SpecificationRunPendingStatus.class);
    }

    @Specification
    public void runsSuccessfully() {
        expect.that(pendingMethod.run().completedSuccessfully()).isTrue();
    }

    @Specification
    public void runsInZeroTime() {
        expect.that(pendingMethod.run().getExecutionTime()).isEqualTo(0L);
    }

    @SuppressWarnings({"ALL"})
    private static final class PendingSpecificationWithReason {
        @Specification(state = PENDING, reason = "Haven't done it yet")
        public void aPendingMethod() {
        }
    }
}
