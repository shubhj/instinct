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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import fj.data.List;
import java.lang.reflect.Method;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ASuperClassTraversingMethodLocator {
    @Subject(implementation = SuperClassTraversingMethodLocatorImpl.class) private SuperClassTraversingMethodLocator locator;

    @BeforeSpecification
    public void before() {
        locator = new SuperClassTraversingMethodLocatorImpl();
    }

    @Specification(reason = "This specification (taken from the examples project) removes methods it shouldn't when run under Ant")
    public void doesNotRemoveMethodsThatAreNotDuplicates() {
        final List<Method> methods = locator.locate(AClassKnownToCauseProblemsWithEqualityForUniqWhenRunUnderAnt.class);
        expect.that(methods).isOfSize(17);
    }

    @RunWith(InstinctRunner.class)
    public final class AClassKnownToCauseProblemsWithEqualityForUniqWhenRunUnderAnt {
        @Subject private Object stack;

        @BeforeSpecification
        void before() {
            stack = new Object();
        }

        @Specification
        void isEmpty() {
            expect.that(stack.hashCode()).isGreaterThan(-1);
        }

        @Specification
        void isNoLongerBeEmptyAfterPush() {
            expect.that(stack.hashCode()).isGreaterThan(-1);
        }

        @Specification(expectedException = IllegalStateException.class, withMessage = "Cannot pop an empty stack")
        void throwsExceptionWhenPopped() {
            throw new IllegalStateException("Cannot pop an empty stack");
        }

        @Specification(state = PENDING, reason = "No idea what we're going to do")
        void hasSomeNewFeatureWeHaveNotThoughtOfYet() {
            expect.that(true).isFalse();
        }
    }
}