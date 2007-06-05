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
import static java.util.Arrays.asList;
import java.util.Collection;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.isA;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubjectWithConstructorArgs;

@SuppressWarnings({"unchecked"})
public final class ContextClassImplAtomicTest extends InstinctTestCase {
    private ContextClass contextClass;
    private Class<?> contextType;
    private ContextRunner contextRunner;
    private ContextResult contextResult;
    private Collection<Method> specMethods;
    private Collection<Method> beforeSpecMethods;
    private Collection<Method> afterSpecMethods;
    private MarkedMethodLocator methodLocator;

    @Override
    public void setUpTestDoubles() {
        contextType = ASimpleContext.class;
        contextRunner = mock(ContextRunner.class);
        methodLocator = mock(MarkedMethodLocator.class);
        contextResult = mock(ContextResult.class);
        specMethods = asList(getMethod(contextType, "toCheckVerification"));
        beforeSpecMethods = asList(getMethod(contextType, "setUp"), getMethod(contextType, "setUpAgain"));
        afterSpecMethods = asList(getMethod(contextType, "tearDown"), getMethod(contextType, "tearDownAgain"));
    }

    @Override
    public void setUpSubject() {
        contextClass = createContextClass(contextType);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextClassImpl.class, ContextClass.class);
    }

    public void testReturnsTypePassedInConstructorFromGetType() {
        expect.that((Object) contextType).sameInstanceAs(contextClass.getType());
    }

    public void testReturnsContextTypeNameSimpleName() {
        expect.that(contextType.getSimpleName()).equalTo(contextClass.getName());
    }

    public void testRunsUsingContextRunner() {
        expects(contextRunner).method("run").with(same(contextClass)).will(returnValue(contextResult));
        expect.that(contextResult).sameInstanceAs(contextClass.run());
    }

    public void testPassesContextListenersToContextRunner() {
        for (int i = 0; i < 3; i++) {
            final ContextListener contextListener = mock(ContextListener.class);
            expects(contextRunner).method("addContextListener").with(same(contextListener));
            contextClass.addContextListener(contextListener);
        }
    }

    public void testPassesSpecificationListenersToContextRunner() {
        for (int i = 0; i < 3; i++) {
            final SpecificationListener specificationListener = mock(SpecificationListener.class);
            expects(contextRunner).method("addSpecificationListener").with(same(specificationListener));
            contextClass.addSpecificationListener(specificationListener);
        }
    }

    public void testReturnsSpecificationsToRun() {
        expects(methodLocator).method("locateAll").with(same(contextType), isA(MarkingScheme.class)).will(returnValue(specMethods));
        final Collection<LifecycleMethod> methods = contextClass.getSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("toCheckVerification"));
    }

    public void testReturnsBeforeSpecificationMethods() {
        expects(methodLocator).method("locateAll").with(same(contextType), isA(MarkingScheme.class)).will(returnValue(beforeSpecMethods));
        final Collection<LifecycleMethod> methods = contextClass.getBeforeSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("setUp"));
        expect.that(methods).containsItem(lifecycleMethod("setUpAgain"));
    }

    public void testReturnsAfterSpecificationMethods() {
        expects(methodLocator).method("locateAll").with(same(contextType), isA(MarkingScheme.class)).will(returnValue(afterSpecMethods));
        final Collection<LifecycleMethod> methods = contextClass.getAfterSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("tearDown"));
        expect.that(methods).containsItem(lifecycleMethod("tearDownAgain"));
    }

    private LifecycleMethod lifecycleMethod(final String methodName) {
        return new LifecycleMethodImpl(getMethod(contextType, methodName));
    }

    private <T> ContextClass createContextClass(final Class<T> contextType) {
        final Object[] constructorArgs = {contextType};
        final Object[] dependencies = {contextRunner, methodLocator};
        return createSubjectWithConstructorArgs(ContextClassImpl.class, constructorArgs, dependencies);
    }
}
