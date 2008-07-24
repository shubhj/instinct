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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.runner.ASimpleNamingConventionContext;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.data.List;
import java.lang.reflect.Method;
import org.jmock.Expectations;
import org.jmock.internal.matcher.MethodNameMatcher;

@SuppressWarnings({"UnusedDeclaration"})
public final class NamedMethodLocatorImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = NamedMethodLocatorImpl.class) private NamedMethodLocator methodLocator;
    @Mock private NamingConvention namingConvention;

    public void testConformsToClassTraits() {
        checkClass(NamedMethodLocatorImpl.class, NamedMethodLocator.class);
    }

    public void testFindsMethodsConformingToTheNamingConvention() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(namingConvention).getPattern();
                will(returnValue("^must.*"));
            }
        });
        final List<Method> methods = methodLocator.locate(ASimpleNamingConventionContext.class, namingConvention);
        expect.that(methods).containsItem(new MethodNameMatcher("^mustAlwaysReturnTrue$"));
    }
}
