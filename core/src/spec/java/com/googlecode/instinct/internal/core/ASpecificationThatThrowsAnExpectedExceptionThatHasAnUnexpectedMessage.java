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
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import static com.googlecode.instinct.internal.util.Reflector.getDeclaredMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import fj.data.List;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
@RunWith(InstinctRunner.class)
public final class ASpecificationThatThrowsAnExpectedExceptionThatHasAnUnexpectedMessage {
    @Subject(auto = false) private ExpectingExceptionSpecificationMethod expectsExceptionMethod;
    @Mock private SpecificationRunner specificationRunner;
    @Dummy private SpecificationResult specificationResult;
    @Stub private List<LifecycleMethod> beforeSpecMethods;
    @Stub private List<LifecycleMethod> afterSpecMethods;

    @BeforeSpecification
    public void before() {
        final Object[] constructorArgs =
                {getDeclaredMethod(ContainsExpectedException.class, "expectsExceptionThrown"), beforeSpecMethods, afterSpecMethods};
        expectsExceptionMethod = createSubjectWithConstructorArgs(ExpectingExceptionSpecificationMethod.class, constructorArgs, specificationRunner);
    }

    @Specification
    public void failsAsTheMessageDoesNotMatch() {
        expect.that(new Expectations() {
            {
                one(specificationRunner).run(expectsExceptionMethod);
                will(throwException(new RuntimeException("This is the wrong message")));
            }
        });
        final SpecificationResult result = expectsExceptionMethod.run();
        expect.that(result.completedSuccessfully()).isFalse();
        expect.that(((Throwable) result.getStatus().getDetailedStatus()).getMessage()).containsString("Expected exception message was incorrect");
    }

    @SuppressWarnings({"ALL"})
    private static final class ContainsExpectedException {
        @Specification(expectedException = RuntimeException.class, withMessage = "This should fail")
        public void expectsExceptionThrown() {
            throw new RuntimeException("This should fail");
        }
    }
}