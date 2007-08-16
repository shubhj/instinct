/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.integrate.junit4;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker12.eq;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.matcher.SpecificationMatcher;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.JUnit4SuiteWithContextAnnotation;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;
import java.util.Collection;
import java.util.HashSet;
import org.hamcrest.Matcher;

public final class SpecificationMethodBuilderImplAtomicTest extends InstinctTestCase {
    private SpecificationMethodBuilder builder;
    private ContextClassesFinder finder;
    private Collection<Class<?>> contextClasses;
    private ObjectFactory objectFactory;
    private ContextClass contextClass;
    private SpecificationMethod specificationMethod;
    private Collection<SpecificationMethod> mockSpecificationMethods;

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodBuilderImpl.class, SpecificationMethodBuilder.class);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void setUpTestDoubles() {
        finder = mock(ContextClassesFinder.class);
        contextClasses = new HashSet<Class<?>>() {
            {
                add(ASimpleContext.class);
            }
        };
        objectFactory = mock(ObjectFactory.class);
        contextClass = mock(ContextClass.class);
        specificationMethod = mock(SpecificationMethod.class);
        mockSpecificationMethods = new HashSet<SpecificationMethod>() {
            {
                add(specificationMethod);
            }
        };
    }

    @Override
    public void setUpSubject() {
        builder = createSubject(SpecificationMethodBuilderImpl.class, finder, objectFactory);
    }

    public void testFindsSpecificationsOnWithContextClassAnnotation() {
        expects(finder).method("getContextClasses").with(eq(JUnit4SuiteWithContextAnnotation.class)).will(returnValue(contextClasses));
        expects(objectFactory).method("create").with(eq(ContextClassImpl.class), eq(new Object[]{ASimpleContext.class})).will(returnValue(contextClass));
        expects(contextClass).method("buildSpecificationMethods").will(returnValue(mockSpecificationMethods));
        final Collection<SpecificationMethod> methods = builder.build(JUnit4SuiteWithContextAnnotation.class);
        expect.that(methods).hasSize(1);
        expect.that(methods).containsItem(specificationMethod);
    }

    private Matcher<SpecificationMethod> aMethodNamed(final String methodName) {
        return new SpecificationMatcher(methodName);
    }
}
