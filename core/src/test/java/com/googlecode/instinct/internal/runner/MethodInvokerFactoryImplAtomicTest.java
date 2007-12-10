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

package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.ASampleClass;
import com.googlecode.instinct.internal.core.ASampleClassWithNoAnnotations;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.MethodInvokerImpl;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.sandbox.ForAll;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import java.lang.annotation.Annotation;
import org.jmock.Expectations;

public final class MethodInvokerFactoryImplAtomicTest extends InstinctTestCase {
    private static final Class<ASampleClass> CLASS_WITH_FOR_ALL_ANNOTATION = ASampleClass.class;
    private static final Class<ASampleClassWithNoAnnotations> CLASS_WITH_NO_ANNOTATION = ASampleClassWithNoAnnotations.class;

    private MethodInvokerFactory methodInvokerFactory;
    @Mock private LifecycleMethod specificationMethod;
    @Mock private ParameterAnnotationFinder finder;
    private Annotation[][] parameterAnnotations;
    private Annotation[][] noAnnotations;

    public void testConformsToClassTraits() {
        checkClass(MethodInvokerFactoryImpl.class, MethodInvokerFactory.class);
    }

    @Override
    public void setUpTestDoubles() {
        parameterAnnotations = CLASS_WITH_FOR_ALL_ANNOTATION.getMethods()[0].getParameterAnnotations();
        noAnnotations = CLASS_WITH_NO_ANNOTATION.getMethods()[0].getParameterAnnotations();
    }

    @Override
    public void setUpSubject() {
        methodInvokerFactory = createSubject(MethodInvokerFactoryImpl.class, finder);
    }

    public void testWillReturnADefaultMethodInvokerForAMethodWithoutForAllAnnotation() {
        expect.that(new Expectations() {
            {
                one(specificationMethod).getParameterAnnotations();
                will(returnValue(noAnnotations));
                one(finder).hasAnnotation(ForAll.class, noAnnotations);
                will(returnValue(false));
            }
        });
        final MethodInvoker methodInvoker = methodInvokerFactory.create(specificationMethod);
        expect.that(methodInvoker).isAnInstanceOf(MethodInvokerImpl.class);
    }

    public void testWillReturnTheoryMethodInvokerForAMethodWithForAllAnnotation() {
        expect.that(new Expectations() {
            {
                one(specificationMethod).getParameterAnnotations();
                will(returnValue(parameterAnnotations));
                one(finder).hasAnnotation(ForAll.class, parameterAnnotations);
                will(returnValue(true));
            }
        });
        final MethodInvoker methodInvoker = methodInvokerFactory.create(specificationMethod);
        expect.that(methodInvoker).isAnInstanceOf(TheoryMethodInvokerImpl.class);
    }
}
