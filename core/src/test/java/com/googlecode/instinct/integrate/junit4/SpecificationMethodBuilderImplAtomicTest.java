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
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.OldDodgySpecificationMethod;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.JUnit4SuiteWithContextAnnotation;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.util.Collection;
import java.util.HashSet;
import org.jmock.Expectations;

@SuppressWarnings({"serial", "CloneableClassWithoutClone", "ClassExtendsConcreteCollection"})
public final class SpecificationMethodBuilderImplAtomicTest extends InstinctTestCase {
    private SpecificationMethodBuilder builder;
    private ContextClassesFinder finder;
    private Collection<Class<?>> contextClasses;
    private ObjectFactory objectFactory;
    private ContextClass contextClass;
    private OldDodgySpecificationMethod specificationMethod;
    private Collection<OldDodgySpecificationMethod> specificationMethods;

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodBuilderImpl.class, SpecificationMethodBuilder.class);
    }

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
        specificationMethod = mock(OldDodgySpecificationMethod.class);
        specificationMethods = new HashSet<OldDodgySpecificationMethod>() {
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
        expect.that(new Expectations() {
            {
                one(finder).getContextClasses(JUnit4SuiteWithContextAnnotation.class);
                will(returnValue(contextClasses));
                one(objectFactory).create(ContextClassImpl.class, ASimpleContext.class);
                will(returnValue(contextClass));
                one(contextClass).buildSpecificationMethods();
                will(returnValue(specificationMethods));
            }
        });
        final Collection<OldDodgySpecificationMethod> methods = builder.build(JUnit4SuiteWithContextAnnotation.class);
        expect.that(methods).isOfSize(1);
        expect.that(methods).containsItem(specificationMethod);
    }
}
