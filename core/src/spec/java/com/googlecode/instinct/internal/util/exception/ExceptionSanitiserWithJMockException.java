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

package com.googlecode.instinct.internal.util.exception;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.SpecificationFailureException;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.jmock.api.ExpectationError;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class ExceptionSanitiserWithJMockException {
    @Subject(implementation = ExceptionSanitiserImpl.class) private ExceptionSanitiser exceptionSanitiser;
    @Dummy(auto = false) private Throwable noExpectations;
    @Dummy(auto = false) private Throwable expectationsNotMet;

    @BeforeSpecification
    public void before() {
        exceptionSanitiser = createSubject(ExceptionSanitiserImpl.class);
        noExpectations = new ExpectationError("no expectations specified", new SelfDescribing() {
            public void describeTo(final Description description) {
            }
        });
        expectationsNotMet = new ExpectationError("Expectatons not met", new SelfDescribing() {
            public void describeTo(final Description description) {
            }
        });
    }

    @Specification
    public void transformsJMockNoExpectationErrorIntoSpecificationFailureException() {
        expect.that(exceptionSanitiser.sanitise(noExpectations)).isOfType(SpecificationFailureException.class);
    }

    @Specification
    public void passesThroughOtherJMockExpectationErrors() {
        expect.that(exceptionSanitiser.sanitise(expectationsNotMet)).isOfType(ExpectationError.class);
    }
}