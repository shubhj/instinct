/*
 * Copyright 2006-2007 Tom Adams
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

package com.googlecode.instinct.internal.aggregate.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.Mocker12;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ClassChecker;
import java.lang.reflect.Method;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.jmock.internal.matcher.MethodNameMatcher;

public final class NamingConventionMethodLocatorImplAtomicTest extends InstinctTestCase {
    private NamingConvention namingConvention;

    public void testConformsToClassTraits() {
        ClassChecker.checkClass(NamingConventionMethodLocatorImpl.class, NamingConventionMethodLocator.class);
    }

    @Override
    public void setUpTestDoubles() {
        super.setUpTestDoubles();
        namingConvention = mock(NamingConvention.class);
    }

    public void testFindsMethodsConformingToTheNamingConvention() {
        final NamingConventionMethodLocator locator = new NamingConventionMethodLocatorImpl();
        expects(namingConvention, Mocker12.atLeastOnce()).method("getPattern").will(returnValue("^must.*"));
        final Collection<Method> methods = locator.locate(ASimpleNamingConventionContext.class, namingConvention);
        final Matcher<Method> aMethodNamedMustAlwaysReturnTrue = new MethodNameMatcher("^mustAlwaysReturnTrue$");
        expect.that(methods).containsItem(aMethodNamedMustAlwaysReturnTrue);
    }
}
