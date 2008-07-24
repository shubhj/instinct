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
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
@RunWith(InstinctRunner.class)
public final class ASpecificationThatThrowsAnExpectedException {
    @Subject(auto = false) private ExpectingExceptionSpecificationMethod expectsExceptionMethod;
    @Stub private List<LifecycleMethod> beforeSpecMethods;
    @Stub private List<LifecycleMethod> afterSpecMethods;

    @BeforeSpecification
    public void before() {
        expectsExceptionMethod = new ExpectingExceptionSpecificationMethod(
                getDeclaredMethod(ContainsExpectedException.class, "expectsExceptionThrown"), beforeSpecMethods, afterSpecMethods);
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(ExpectingExceptionSpecificationMethod.class, SpecificationMethod.class);
    }

    @Specification
    public void exposesTheContextClass() {
        expect.that(expectsExceptionMethod.getContextClass().equals(ContainsExpectedException.class)).isTrue();
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
        final SpecificationResult result = expectsExceptionMethod.run();
        expect.that(result.completedSuccessfully()).isTrue();
    }

    @SuppressWarnings({"ALL"})
    private static final class ContainsExpectedException {
        @Specification(expectedException = RuntimeException.class, withMessage = "This should fail")
        public void expectsExceptionThrown() {
            throw new RuntimeException("This should fail");
        }
    }
}