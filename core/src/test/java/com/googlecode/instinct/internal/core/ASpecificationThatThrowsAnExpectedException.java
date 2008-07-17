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
import com.googlecode.instinct.internal.runner.CompleteSpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class ASpecificationThatThrowsAnExpectedException {
    @Subject(auto = false) private ExpectingExceptionSpecificationMethod expectsExceptionMethod;
    @Mock private CompleteSpecificationRunner specificationRunner;
    @Dummy private SpecificationResult specificationResult;

    @BeforeSpecification
    public void before() {
        final Object[] constructorArgs = {getDeclaredMethod(ContainsExpectedException.class, "expectsExceptionThrown")};
        expectsExceptionMethod = createSubjectWithConstructorArgs(ExpectingExceptionSpecificationMethod.class, constructorArgs, specificationRunner);
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(ExpectingExceptionSpecificationMethod.class, SpecificationMethod.class);
    }

    @Specification
    public void hasANameThatComesFromTheUnderlyingMethod() {
        expect.that(expectsExceptionMethod.getName()).isEqualTo("expectsExceptionThrown");
    }

    @Specification
    public void exposesTheExpectedException() {
        expect.that(expectsExceptionMethod.getExpectedException().equals(RuntimeException.class)).isTrue();
    }

    @Specification
    public void exposesTheExceptionMessage() {
        expect.that(expectsExceptionMethod.getExpectedExceptionMessage()).isEqualTo("This should fail");
    }

    @Specification
    public void runsSpecsUsingTheNormalSpecificationRunner() {
        expect.that(new Expectations() {
            {
                one(specificationRunner).run(expectsExceptionMethod);
                will(returnValue(specificationResult));
            }
        });
        expect.that(expectsExceptionMethod.run()).isEqualTo(specificationResult);
    }

    @Specification
    public void throwsErrorsWhenSpecsExpectingExceptionsDoNotThrowExceptions() {
    }

    @SuppressWarnings({"ALL"})
    private static final class ContainsExpectedException {
        @Specification(expectedException = RuntimeException.class, withMessage = "This should fail")
        public void expectsExceptionThrown() {
            throw new RuntimeException("This should fail");
        }
    }
}