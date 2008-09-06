/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AParameterTraitCheckerImpl {
    @Subject private ParameterTraitChecker checker;
    @Mock InstanceProvider instanceProvider;

    @BeforeSpecification
    public void setUp() {
        checker = new ParameterTraitCheckerImpl(instanceProvider);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void cannotBeContstructedWithANullInstanceProvider() {
        new ParameterTraitCheckerImpl(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptANullClassWhenCheckingThatPublicConstructorsRejectNull() {
        checker.checkPublicConstructorsRejectsNull(null);
    }

    @Specification
    public void willIgnoreDefaultConstructorsWhenNullParamCheckingOccurs() {
        checker.checkPublicConstructorsRejectsNull(AClassWithDefaultConstructor.class);
    }

    @Specification(expectedException = AssertionError.class,
            withMessage = "Argument 1 of AClassWithASingleConstructorParamWhichIsNotChecked(...String...) must be null checked")
    public void willFailAssertionWhenSingleParamConstructorsDoesNotCheckForNullValues() {
        expect.that(new Expectations() {
            {
                one(instanceProvider).newInstance(String.class);
                will(returnValue(null));
            }
        });
        checker.checkPublicConstructorsRejectsNull(AClassWithASingleConstructorParamWhichIsNotChecked.class);
    }

    @Specification
    public void willPassAssertionWhenSingleParamConstructorsDoesCheckForNullValues() {
        expect.that(new Expectations() {
            {
                one(instanceProvider).newInstance(String.class);
                will(returnValue(null));
            }
        });
        checker.checkPublicConstructorsRejectsNull(AClassWithASingleConstructorParamWhichIsChecked.class);
    }

    @Specification(expectedException = AssertionError.class,
            withMessage = "Argument 3 of AClassWithAManyConstructorParamsOfWhichOneIsNotChecked(...Boolean...) must be null checked")
    public void willFailAssertionWhenOneOfManyParametersIsNotNullChecked() {
        expect.that(new Expectations() {
            {
                // null checking the first param
                one(instanceProvider).newInstance(String.class);
                will(returnValue(null));
                one(instanceProvider).newInstance(Object.class);
                will(returnValue("an_object"));
                one(instanceProvider).newInstance(Boolean.class);
                will(returnValue(false));
                // null checking the second param
                one(instanceProvider).newInstance(String.class);
                will(returnValue("a_string"));
                one(instanceProvider).newInstance(Object.class);
                will(returnValue(null));
                one(instanceProvider).newInstance(Boolean.class);
                will(returnValue(false));
                // null checking the third param
                one(instanceProvider).newInstance(String.class);
                will(returnValue("a_string"));
                one(instanceProvider).newInstance(Object.class);
                will(returnValue("an_object"));
                one(instanceProvider).newInstance(Boolean.class);
                will(returnValue(null));
            }
        });
        checker.checkPublicConstructorsRejectsNull(AClassWithAManyConstructorParamsOfWhichOneIsNotChecked.class);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptANullClassWhenCheckingThatPublicConstructorsRejectEmptyString() {
        checker.checkPublicConstructorsRejectEmptyString(null);
    }

    @Specification
    public void willIgnoreDefaultConstructorsWhenCheckingThatPublicConstructorsRejectEmptyString() {
        checker.checkPublicConstructorsRejectEmptyString(AClassWithDefaultConstructor.class);
    }

    @Specification(expectedException = AssertionError.class,
            withMessage = "Argument 1 of AClassWithASingleConstructorParamWhichIsNotChecked(...String...) must be empty string checked")
    public void willFailAssertionWhenSingleParamConstructorsDoesNotCheckForEmptyString() {
        expect.that(new Expectations() {
            {
                one(instanceProvider).newInstance(String.class);
                will(returnValue(" "));
            }
        });
        checker.checkPublicConstructorsRejectEmptyString(AClassWithASingleConstructorParamWhichIsNotChecked.class);
    }

    @Specification
    public void willPassAssertionWhenSingleParamConstructorsDoesCheckForEmptyString() {
        expect.that(new Expectations() {
            {
                exactly(2).of(instanceProvider).newInstance(String.class);
                will(returnValue(" "));
            }
        });
        checker.checkPublicConstructorsRejectEmptyString(AClassWithASingleConstructorParamWhichIsChecked.class);
    }
}

