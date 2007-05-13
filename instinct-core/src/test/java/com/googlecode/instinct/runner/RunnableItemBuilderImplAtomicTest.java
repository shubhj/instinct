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

package com.googlecode.instinct.runner;

import java.util.Collection;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.METHOD_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.AssertThrowsChecker.assertThrows;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

public final class RunnableItemBuilderImplAtomicTest extends InstinctTestCase {
    private RunnableItemBuilder runnableItemBuilder;
    private ClassInstantiator classInstantiator;
    private static final Class<ASimpleContext> CONTEXT_CLASS_1 = ASimpleContext.class;

    @Override
    public void setUpTestDoubles() {
        classInstantiator = mock(ClassInstantiator.class);
    }

    @Override
    public void setUpSubject() {
        runnableItemBuilder = createSubject(RunnableItemBuilderImpl.class, classInstantiator);
    }

    public void testConformsToClassTraits() {
        checkClass(RunnableItemBuilderImpl.class, RunnableItemBuilder.class);
    }

    public void testContainsMethodSeparatorConstant() {
        expect.that(METHOD_SEPARATOR).equalTo("#");
    }

    public void testBuildsSingleContextNameIntoContextClass() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        final Collection<RunnableItem> itemsToRun = runnableItemBuilder.build(CONTEXT_CLASS_1.getName());
        expect.that(itemsToRun).hasSize(1);
        expectRunnableItemIsAContextClass(itemsToRun.iterator().next(), CONTEXT_CLASS_1);
    }

    public void testBuildsASingleSpecificationMethodNameIntoSpecificationMethod() {
        expects(classInstantiator).method("instantiateClass").with(eq(CONTEXT_CLASS_1.getName())).will(returnValue(CONTEXT_CLASS_1));
        final String specificationMethod = CONTEXT_CLASS_1.getName() + METHOD_SEPARATOR + "toCheckVerification";
        final Collection<RunnableItem> itemsToRun = runnableItemBuilder.build(specificationMethod);
        expect.that(itemsToRun).hasSize(1);
        expectRunnableItemIsASpecificationMethod(itemsToRun.iterator().next(), "toCheckVerification");
    }

    public void testRejectsSpecsMarkedWithTwoMethods() {
        assertThrows(IllegalArgumentException.class, "Specifications to run cannot contain more than one " + METHOD_SEPARATOR, new Runnable() {
            public void run() {
                runnableItemBuilder.build("ClassName" + METHOD_SEPARATOR + "specName" + METHOD_SEPARATOR + "anotherSpec");
            }
        });
    }

    public void testRejectsUnknownSpecs() {
    }

    private void expectRunnableItemIsASpecificationMethod(final RunnableItem runnableItem, final String specificationMethod) {
        expect.that(runnableItem.getClass()).typeCompatibleWith(SpecificationMethod.class);
        expect.that(((SpecificationMethod) runnableItem).getName()).equalTo(specificationMethod);
    }

    private <T> void expectRunnableItemIsAContextClass(final RunnableItem runnableItem, final Class<T> expectedWrappedClass) {
        expect.that(runnableItem.getClass()).typeCompatibleWith(ContextClass.class);
        expect.that(((ContextClass) runnableItem).getType()).typeCompatibleWith(expectedWrappedClass);
    }
}
