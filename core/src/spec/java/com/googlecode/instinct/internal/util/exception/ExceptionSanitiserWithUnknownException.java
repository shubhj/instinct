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
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ExceptionSanitiserWithUnknownException {
    @Subject(implementation = ExceptionSanitiserImpl.class) private ExceptionSanitiser exceptionSanitiser;
    @Dummy(auto = false) private Throwable exception;

    @BeforeSpecification
    public void before() {
        exceptionSanitiser = createSubject(ExceptionSanitiserImpl.class);
        exception = new NotNeedingSanitisation();
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(ExceptionSanitiserImpl.class, ExceptionSanitiser.class);
    }

    @Specification
    public void passesExceptionThroughUnchanged() {
        expect.that(exceptionSanitiser.sanitise(exception)).isEqualTo(exception);
    }

    @SuppressWarnings({"ALL"})
    private static final class NotNeedingSanitisation extends Throwable {
        private static final long serialVersionUID = -7221958359176000824L;
    }
}
