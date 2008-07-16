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
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class APendingSpecificationMethodWithAReason {
    @Subject(auto = false) private PendingSpecificationMethod pendingMethod;

    @BeforeSpecification
    public void before() {
        pendingMethod = new PendingSpecificationMethod(getDeclaredMethod(PendingSpecificationWithReason.class, "aPendingMethod"));
    }

    @Specification
    public void hasANameThatComesFromTheUnderlyingMethod() {
        expect.that(pendingMethod.getName()).isEqualTo("aPendingMethod");
    }

    @Specification
    public void pullsThePendingReasonOffTheAnnotation() {
        final String reason = pendingMethod.getPendingReason();
        expect.that(reason).isEqualTo("Haven't done it yet");
    }

    @SuppressWarnings({"ALL"})
    private static final class PendingSpecificationWithReason {
        @Specification(state = PENDING, reason = "Haven't done it yet")
        public void aPendingMethod() {
        }
    }
}
