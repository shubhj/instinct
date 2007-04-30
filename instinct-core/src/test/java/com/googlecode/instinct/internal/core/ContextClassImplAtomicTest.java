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

package com.googlecode.instinct.internal.core;

import java.lang.reflect.Method;
import java.util.Collection;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubjectWithConstructorArgs;

@SuppressWarnings({"unchecked"})
public final class ContextClassImplAtomicTest extends InstinctTestCase {
    private ContextClass contextClass;
    private Class<?> contextType;
    private ContextRunner contextRunner;
    private ContextResult contextResult;
    private Collection<Method> methods;
    private MarkedMethodLocator methodLocator;

    @Override
    public void setUpTestDoubles() {
        contextType = ASimpleContext.class;
        contextRunner = mock(ContextRunner.class);
        methodLocator = mock(MarkedMethodLocator.class);
        contextResult = mock(ContextResult.class);
        methods = mock(Collection.class);
    }

    @Override
    public void setUpSubject() {
        contextClass = createContextClass(contextType);
    }

    @Fix("Test extension of primordial.")
    public void testConformsToClassTraits() {
        checkClass(ContextClassImpl.class, ContextClass.class);
    }

    public void testReturnsTypePassedInConstructorFromGetType() {
        assertSame(contextType, contextClass.getType());
    }

    public void testReturnsContextTypeNameSimpleName() {
        assertEquals(contextType.getSimpleName(), contextClass.getName());
    }

    public void testRunsContextUsingContextRunner() {
        expects(contextRunner).method("run").with(same(contextType)).will(returnValue(contextResult));
        assertSame(contextResult, contextClass.run());
    }

    public void testNotifiesContextRunListenersWhenRun() {
        expectContextRunListenersStored();
        expects(contextRunner).method("run").with(same(contextType)).will(returnValue(contextResult));
        contextClass.run();
    }

    @Fix("Check more than number, check name.")
    public void testReturnsSpecificationsToRun() {
//        expectSpecificationMethodsFound();
        final ContextClass context = createContextClass(ASimpleContext.class);
        final Collection<SpecificationMethod> methods = context.getSpecificationMethods();
        assertEquals(1, methods.size());
        assertEquals("toCheckVerification", methods.iterator().next().getName());
    }

/*
To drive out:
Method[] getBeforeSpecificationMethods();
Method[] getAfterSpecificationMethods();
Method getSpecificationMethod();
*/

    private void expectContextRunListenersStored() {
        for (int i = 0; i < 3; i++) {
            final ContextRunListener contextRunListener = mock(ContextRunListener.class);
            expects(contextRunListener).method("onContext").with(same(contextClass));
            contextClass.addRunListener(contextRunListener);
        }
    }

    @Suggest("Boost's equals is broken for classes without fields.")
    private void expectSpecificationMethodsFound() {
        final MarkingScheme markingScheme = new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention());
        expects(methodLocator).method("locateAll").with(same(contextType), eq(markingScheme)).will(returnValue(methods));
    }

    private <T> ContextClass createContextClass(final Class<T> contextType) {
        final Object[] constructorArgs = {contextType};
//        final Object[] dependencies = {contextRunner, methodLocator};
        final Object[] dependencies = {contextRunner};
        return createSubjectWithConstructorArgs(ContextClassImpl.class, constructorArgs, dependencies);
    }
}
